#built using mc-build (https://github.com/mc-build/mc-build)

execute as @e[type=minecraft:item_frame, tag=CornPlant, tag=placed, nbt={Item:{id:"minecraft:item_frame"}}] at @s run function template:corn/check_wet