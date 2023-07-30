import org.bytedeco.javacpp.indexer.DoubleIndexer
import org.bytedeco.javacpp.indexer.Indexer
import org.bytedeco.javacpp.indexer.ShortIndexer
import org.bytedeco.javacpp.indexer.UByteIndexer
import org.bytedeco.opencv.global.opencv_core.CV_16S
import org.bytedeco.opencv.opencv_core.Mat
import org.bytedeco.opencv.opencv_videoio.VideoWriter
import org.bytedeco.opencv.opencv_videostab.IFrameSource



var stabilizedFrames = IFrameSource()
var outputPath = String()
var outputFps : Double = 32.0

fun run() {
    val writer = VideoWriter()
    var stabilizedFrame: Mat = Mat()
    var nframes: Int = 0;
    var fileName: String

    stabilizedFrame = stabilizedFrames.nextFrame()
    while (!stabilizedFrame.empty()) {
        nframes++;

        // init writer (once) and save stabilized frame
        if (outputPath.isNotEmpty()) {
            if (!writer.isOpened) {
                val fourcc = VideoWriter.fourcc(
                    'X'.code.toByte(),
                    'V'.code.toByte(),
                    'I'.code.toByte(),
                    'D'.code.toByte()
                )
                writer.open(outputPath, fourcc, outputFps, stabilizedFrame.size())
            }
            writer.write(stabilizedFrame)
        }




        stabilizedFrame = stabilizedFrames.nextFrame()
    }
}

fun dumpMat(m : Mat) {
    val i2 : ShortIndexer = m.createIndexer()
    // val idx : Indexer = m.createIndexer()
    for (i in 0 until m.rows()) {
        for (j in 0 until m.cols()) {
            print(i2.get(i.toLong(), j.toLong()))
        }
        println()
    }
}

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
    // System.loadLibrary(org.bytedeco.opencv.NATIVE_LIBRARY_NAME)
    val m : Mat? = Mat.eye(5, 5, CV_16S).asMat()

    dumpMat(m!!)

}