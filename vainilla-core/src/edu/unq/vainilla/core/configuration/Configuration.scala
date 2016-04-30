package edu.unq.vainilla.core.configuration

import com.badlogic.gdx.Files
import com.badlogic.gdx.Files.FileType
import com.badlogic.gdx.graphics.Color

import scala.collection.mutable.ArrayBuffer

trait Configuration {

  /** title of application **/
  var title = "My Vanilla Game"

  /** width & height of application window **/
  var width = 640
  var height = 480

  /** fullscreen **/
  var fullscreen = false

  /** x & y of application window, -1 for center **/
  var x = -1
  var y = -1

  /** number of bits per color channel **/
  var r = 8
  var g = 8
  var b = 8
  var a = 8

  /** number of bits for depth and stencil buffer **/
  var depth = 16
  var stencil = 0

  /** number of samples for MSAA **/
  var samples = 0

  /** used to emulate screen densities **/
  var overrideDensity = -1

  /** whether to enable vsync **/
  var vSyncEnabled = true

  /** whether to call System.exit() on tear-down. Needed for Webstarts on some versions of Mac OS X it seems **/
  var forceExit = true

  /** whether the window is resizable **/
  var resizable = false

  /** If true, OpenAL will not be used **/
  var disableAudio = false

  /** the maximum number of sources that can be played simultaneously */
  var audioDeviceSimultaneousSources = 16

  /** the audio device buffer size in samples **/
  var audioDeviceBufferSize = 512

  /** the audio device buffer count **/
  var audioDeviceBufferCount = 9

  var initialBackgroundColor = Color.BLACK

  /** Target framerate when the window is in the foreground. The CPU sleeps as needed. Use 0 to never sleep. **/
  var foregroundFPS = 60

  /** Target framerate when the window is not in the foreground. The CPU sleeps as needed. Use 0 to never sleep, -1 to not render. **/
  var backgroundFPS = 60

  /** Allows software OpenGL rendering if hardware acceleration was not available. **/
  var allowSoftwareMode = false

  /** Preferences directory on the desktop. Default is ".prefs/". */
  var preferencesDirectory = ".prefs/"

  /** Preferences file type on the desktop. Default is FileType.External */
  var preferencesFileType: Files.FileType = FileType.External

  /** enable HDPI mode on Mac OS X **/
  var useHDPI = false

  val iconInfo = ArrayBuffer.empty[(String, FileType)]

  /** Adds a window icon. Icons are tried in the order added, the first one that works is used. Typically three icons should be
    * provided: 128x128 (for Mac), 32x32 (for Windows and Linux), and 16x16 (for Windows). */
  def addIcon(path: String, fileType: FileType): Unit = iconInfo.+=((path, fileType))

}

class SimpleConfiguration extends Configuration

trait Configurator {

  def configure(config: Configuration): Configuration

}
