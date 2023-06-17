package com.pratwib.consumzy2.util

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import com.pratwib.consumzy2.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

private const val FILENAME_FORMAT = "dd-MMM-yyyy"

private const val MAXIMAL_SIZE = 1000000

val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

fun convertLongToDate(timestamp: Long): String {
    val date = Date(timestamp)
    val dateFormat = SimpleDateFormat("MMMM dd", Locale.getDefault())
    return dateFormat.format(date)
}

fun getDayDifference(timestamp1: Long, timestamp2: Long): Long {
    val calendar1 = Calendar.getInstance()
    calendar1.timeInMillis = timestamp1

    val calendar2 = Calendar.getInstance()
    calendar2.timeInMillis = timestamp2

    // Hapus informasi jam, menit, detik, dan milidetik
    calendar1.set(Calendar.HOUR_OF_DAY, 0)
    calendar1.set(Calendar.MINUTE, 0)
    calendar1.set(Calendar.SECOND, 0)
    calendar1.set(Calendar.MILLISECOND, 0)

    calendar2.set(Calendar.HOUR_OF_DAY, 0)
    calendar2.set(Calendar.MINUTE, 0)
    calendar2.set(Calendar.SECOND, 0)
    calendar2.set(Calendar.MILLISECOND, 0)

    val differenceInMillis = calendar2.timeInMillis - calendar1.timeInMillis

    return differenceInMillis / (24 * 60 * 60 * 1000)
}

fun progressBarHorizontal(past: Long, now: Long, later: Long): Int {
    val totalDuration = later - past
    val currentDuration = now - past

    return ((currentDuration.toFloat() / totalDuration) * 100).toInt()
}

fun createTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun createFile(application: Application): File {
    val mediaDir = application.externalMediaDirs.firstOrNull()?.let {
        File(it, application.resources.getString(R.string.app_name)).apply { mkdirs() }
    }
    val outputDirectory = if (
        mediaDir != null && mediaDir.exists()
    ) mediaDir else application.filesDir
    return File(outputDirectory, "$timeStamp.jpg")
}

fun rotateFile(file: File, isBackCamera: Boolean = false) {
    val matrix = Matrix()

    val bitmap = BitmapFactory.decodeFile(file.path)

    val rotation = if (isBackCamera) 0f else 0f
    matrix.postRotate(rotation)
    if (!isBackCamera) {
        matrix.postScale(-1f, 1f, bitmap.width / 2f, bitmap.height / 2f)
    }
    val result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    result.compress(Bitmap.CompressFormat.JPEG, 100, FileOutputStream(file))
}

fun uriToFile(selectedImg: Uri, context: Context): File {
    val contentResolver: ContentResolver = context.contentResolver

    val myFile = createTempFile(context)

    val inputStream = contentResolver.openInputStream(selectedImg) as InputStream

    val outputStream: OutputStream = FileOutputStream(myFile)

    val buf = ByteArray(1024)

    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
    outputStream.close()
    inputStream.close()

    return myFile
}

fun reduceFileImage(file: File): File {
    val bitmap = BitmapFactory.decodeFile(file.path)

    var compressQuality = 100

    var streamLength: Int
    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > MAXIMAL_SIZE)
    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
    return file
}