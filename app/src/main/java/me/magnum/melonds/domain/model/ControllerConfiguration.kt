package me.magnum.melonds.domain.model

import java.util.*

class ControllerConfiguration(configList: List<InputConfig>) {
    companion object {
        private val configurableInput = arrayOf(
                Input.A,
                Input.B,
                Input.X,
                Input.Y,
                Input.LEFT,
                Input.RIGHT,
                Input.UP,
                Input.DOWN,
                Input.L,
                Input.R,
                Input.START,
                Input.SELECT,
                Input.HINGE,
                Input.PAUSE,
                Input.FAST_FORWARD,
                Input.MICROPHONE,
                Input.RESET,
                Input.SWAP_SCREENS,
                Input.QUICK_SAVE,
                Input.QUICK_LOAD,
                Input.REWIND
        )

        fun empty(): ControllerConfiguration {
            val inputConfigs = ArrayList<InputConfig>()
            for (input in configurableInput) {
                inputConfigs.add(InputConfig(input))
            }
            return ControllerConfiguration(inputConfigs)
        }
    }

    val inputMapper: List<InputConfig>

    init {
        val actualConfig = ArrayList<InputConfig>()
        for (input in configurableInput) {
            var inputConfig: InputConfig? = null
            for (config in configList) {
                if (config.input === input) {
                    inputConfig = config
                    break
                }
            }
            if (inputConfig != null)
                actualConfig.add(inputConfig)
            else
                actualConfig.add(InputConfig(input))
        }
        this.inputMapper = actualConfig
    }

    fun keyToInput(key: Int): Input? {
        for (i in inputMapper.indices) {
            if ((inputMapper[i].assignment as? InputConfig.Assignment.Key)?.keyCode == key)
                return inputMapper[i].input
        }
        return null
    }

    fun axisToInput(axis: Int, direction: InputConfig.Assignment.Axis.Direction): Input? {
        return inputMapper.firstOrNull {
            (it.assignment as? InputConfig.Assignment.Axis)?.let {
                it.axisCode == axis && it.direction == direction
            } ?: false
        }?.input
    }
}