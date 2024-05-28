#built using mc-build (https://github.com/mc-build/mc-build)

playsound minecraft:block.wood.break block @a[distance=0..5] ~ ~ ~ 1 1.2 1
summon item ~ ~0.5 ~ {Item:{id:"minecraft:glow_item_frame",Count:1b,tag:{display:{Name:'{"text":"Planter"}'},CustomModelData:34201,EntityTag:{Silent:1b,Tags:["planter"],Item:{id:"minecraft:glow_item_frame",Count:1b,tag:{CustomModelData:34201}},Invulnerable:1b,Invisible:1b,Fixed:1b}}}}
kill @e[type=item,nbt={Item:{id:"minecraft:glass"}},distance=0..2,sort=nearest,limit=1]
kill @s