#built using mc-build (https://github.com/mc-build/mc-build)

execute as @e[type=minecraft:item_frame, tag=CornPlant, tag=!placed] at @s if block ~ ~-1 ~ servermod:planter run function template:corn/plant
execute as @e[type=minecraft:item_frame, tag=CornPlant, tag=!placed] at @s unless block ~ ~-1 ~ servermod:planter run function template:corn/break
execute as @e[type=minecraft:item_frame, tag=CornPlant, tag=placed] at @s unless block ~ ~-1 ~ servermod:planter run function template:corn/break
execute as @e[type=minecraft:item_frame, tag=CornPlant, tag=placed] at @s unless entity @e[distance=..1, nbt={Item:{id:"minecraft:item_frame"}}] run function template:corn/break