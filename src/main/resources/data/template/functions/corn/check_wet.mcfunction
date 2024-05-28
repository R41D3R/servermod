#built using mc-build (https://github.com/mc-build/mc-build)

execute at @s if block ~ ~-1 ~ servermod:planter[wet=true] run function template:corn/change_models_of_plants
setblock ~ ~-1 ~ servermod:planter[wet=false] replace