#built using mc-build (https://github.com/mc-build/mc-build)

setblock ~ ~ ~ minecraft:oak_planks
playsound minecraft:block.wood.place block @a[distance=0..5] ~ ~ ~ 1 1.2 1
particle minecraft:cloud ~ ~ ~ 0 0 0 1 10
tag @s add placed