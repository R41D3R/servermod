#built using mc-build (https://github.com/mc-build/mc-build)

execute as @e[type=minecraft:glow_item_frame,tag=planter,tag=!placed] at @s run function template:planter/place
execute as @e[type=minecraft:glow_item_frame,tag=planter,tag=placed] at @s unless block ~ ~ ~ minecraft:oak_planks run function template:planter/remove