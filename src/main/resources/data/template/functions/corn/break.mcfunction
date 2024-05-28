#built using mc-build (https://github.com/mc-build/mc-build)

say break
execute as @s run say its meerer
execute as @e[type=minecraft:item_frame, tag=CornPlant, nbt={Item:{id:"minecraft:item_frame", tag:{CustomModelData:34103}}}, distance=..0.5] run function template:corn/harvest_full
execute as @e[type=minecraft:item_frame, tag=CornPlant, nbt={Item:{id:"minecraft:item_frame", tag:{CustomModelData:34102}}}, distance=..0.5] run function template:corn/harvest_not_full
function template:corn/destroy