import org.bytedeco.javacpp.indexer.ShortIndexer
import org.bytedeco.opencv.global.opencv_core.CV_16S
import org.bytedeco.opencv.global.opencv_highgui.waitKey
import org.bytedeco.opencv.opencv_core.Mat
import org.bytedeco.opencv.opencv_videoio.VideoWriter
import org.bytedeco.opencv.opencv_videostab.IFrameSource







var stabilizedFrames = IFrameSource()
var outputPath = String()
var outputFps : Double = 32.0
var quietMode = false

fun run() {
    val writer = VideoWriter()
    var stabilizedFrame: Mat
    var nframes: Int = 0
    var fileName: String
    val imshow : ImShow? = when {
        !quietMode -> ImShow("stabilizedFrame")
        else -> null
    }

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

        if (!quietMode) {
            /*
            val conv1 = OpenCVFrameConverter.ToMat()
            val conv2 = OpenCVFrameConverter.ToOrgOpenCvCoreMat()
            val cvmat : org.opencv.core.Mat = conv2.convert(conv1.convert(stabilizedFrame))
             */

            imshow!!.showImage(stabilizedFrame)

            /*
                sprintf(file_name, "%0.3d.tif", nframes);
                imwrite(file_name, stabilizedFrame);
            */

            val k = waitKey(3)
            if (k == 27) {
                // close file?
                println()
                break
            }
        }
        stabilizedFrame = stabilizedFrames.nextFrame()
    }
    println("processed frames: $nframes")
    println("finished")
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
    // val cmd = ArgParser()

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
    // System.loadLibrary(org.bytedeco.opencv.NATIVE_LIBRARY_NAME)
    val m : Mat? = Mat.eye(5, 5, CV_16S).asMat()

    dumpMat(m!!)




}



public interface IMotionEstimatorBuilder {

}