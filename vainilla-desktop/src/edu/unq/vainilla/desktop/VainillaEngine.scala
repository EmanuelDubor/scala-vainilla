package edu.unq.vainilla.desktop

import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}
import edu.unq.vainilla.core.VainillaGame
import edu.unq.vainilla.core.configuration.{Configuration, Configurator, SimpleConfiguration}
import edu.unq.vainilla.core.gamescene.GameScene
import edu.unq.vainilla.core.input._

import scala.collection.mutable.ListBuffer

object VainillaEngine {

  var mainScene: GameScene = _
  var inputHandler: InputHandler = new BasicInputHandler
  val configurators = ListBuffer.empty[Configurator]

  def start: Unit = {
    var config: Configuration = new SimpleConfiguration
    config = configurators.foldLeft(config) { (config, configurator) => configurator.configure(config) }
    VainillaGame.config = config
    VainillaGame.mainScene = mainScene
    VainillaGame.inputHandler = inputHandler
    VainillaGame.gdxInputProcessor = VainillaInputProcessor

    new LwjglApplication(VainillaGame, config)
  }

  /** Use this if you want your scenes to handle user input **/
  def delegateInput = inputHandler = SceneDelegatorInputHandler

  implicit def vanillaToLwjgConfiguration(config: Configuration): LwjglApplicationConfiguration = {
    val lwjglConfig = new LwjglApplicationConfiguration

    lwjglConfig.r = config.r
    lwjglConfig.g = config.g
    lwjglConfig.b = config.b
    lwjglConfig.a = config.a
    lwjglConfig.depth = config.depth
    lwjglConfig.stencil = config.stencil
    lwjglConfig.samples = config.samples
    lwjglConfig.width = config.width
    lwjglConfig.height = config.height
    lwjglConfig.x = config.x
    lwjglConfig.y = config.y
    lwjglConfig.fullscreen = config.fullscreen
    lwjglConfig.overrideDensity = config.overrideDensity
    lwjglConfig.vSyncEnabled = config.vSyncEnabled
    lwjglConfig.title = config.title
    lwjglConfig.forceExit = config.forceExit
    lwjglConfig.resizable = config.resizable
    lwjglConfig.audioDeviceSimultaneousSources = config.audioDeviceSimultaneousSources
    lwjglConfig.audioDeviceBufferSize = config.audioDeviceBufferSize
    lwjglConfig.audioDeviceBufferCount = config.audioDeviceBufferCount
    lwjglConfig.initialBackgroundColor = config.initialBackgroundColor
    lwjglConfig.foregroundFPS = config.foregroundFPS
    lwjglConfig.backgroundFPS = config.backgroundFPS
    lwjglConfig.allowSoftwareMode = config.allowSoftwareMode
    lwjglConfig.preferencesDirectory = config.preferencesDirectory
    lwjglConfig.preferencesFileType = config.preferencesFileType
    lwjglConfig.useHDPI = config.useHDPI

    LwjglApplicationConfiguration.disableAudio = config.disableAudio

    config.iconInfo.foreach(pair => lwjglConfig.addIcon(pair._1, pair._2))

    lwjglConfig
  }

}
