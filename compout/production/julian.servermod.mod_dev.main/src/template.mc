import ./macros/example.mcm



function load{
    say loader
    scoreboard objectives add test dummy

    
}

function tick{
    function template:corn/corn_tick
    function template:planter/planter_tick
}

dir corn {
    function corn_tick {
        # if farmland
        execute as @e[type=minecraft:item_frame, tag=CornPlant, tag=!placed] at @s if block ~ ~-1 ~ servermod:planter run function template:corn/plant
        
        # if not farmland
        execute as @e[type=minecraft:item_frame, tag=CornPlant, tag=!placed] at @s unless block ~ ~-1 ~ servermod:planter run function template:corn/break
        # if farmland gets trampled
        execute as @e[type=minecraft:item_frame, tag=CornPlant, tag=placed] at @s unless block ~ ~-1 ~ servermod:planter run function template:corn/break

        # if gets broken
        execute as @e[type=minecraft:item_frame, tag=CornPlant, tag=placed] at @s unless entity @e[distance=..1, nbt={Item:{id:"minecraft:item_frame"}}] run function template:corn/break
    }

    function plant {
        # check block above
        # execute as @e[type=minecraft:item_frame, tag=CornPlant, tag=!placed] at @s unless block ~ ~1 ~ minecraft:air run function template:corn/break
        say plant
        tag @s add placed
        summon interaction ~ ~ ~ {width:1f,height:1f,Tags:["CornPlant"]}
    }

    function grow {
        execute as @e[type=minecraft:item_frame, tag=CornPlant, tag=placed, nbt={Item:{id:"minecraft:item_frame"}}] at @s run function template:corn/check_wet
        # execute as @e[type=minecraft:item_frame, tag=CornPlant, tag=placed, nbt={Item:{id:"minecraft:item_frame", tag:{CustomModelData:34102}}}] at @s run data modify entity @s Item.tag.CustomModelData set value 34103
        # # data modify entity @s CustomModelData set value 34103
    }

    function check_wet {
        execute at @s if block ~ ~-1 ~ servermod:planter[wet=true] run function template:corn/change_models_of_plants
        setblock ~ ~-1 ~ servermod:planter[wet=false] replace
    }

    function change_models_of_plants {
        execute as @s[type=minecraft:item_frame, tag=CornPlant, tag=placed, nbt={Item:{id:"minecraft:item_frame", tag:{CustomModelData:34105}}}] at @s run data modify entity @s Item.tag.CustomModelData set value 34106 
        execute as @s[type=minecraft:item_frame, tag=CornPlant, tag=placed, nbt={Item:{id:"minecraft:item_frame", tag:{CustomModelData:34104}}}] at @s run data modify entity @s Item.tag.CustomModelData set value 34105
        execute as @s[type=minecraft:item_frame, tag=CornPlant, tag=placed, nbt={Item:{id:"minecraft:item_frame", tag:{CustomModelData:34103}}}] at @s run data modify entity @s Item.tag.CustomModelData set value 34104
        execute as @s[type=minecraft:item_frame, tag=CornPlant, tag=placed, nbt={Item:{id:"minecraft:item_frame", tag:{CustomModelData:34102}}}] at @s run data modify entity @s Item.tag.CustomModelData set value 34103
        
    }

    function harvest_full {
        
        # summon item ~ ~ ~ {Item:{id:"minecraft:diamond", Count:1b}}
        # /summon minecraft:item <x y z> {Item:{id:"minecraft:stone", Count:1b}}
        loot spawn ~ ~0.5 ~ loot template:corn_full
        playsound minecraft:block.weeping_vines.break block @a
        say harvest_full
    }

    function harvest_not_full {
        loot spawn ~ ~0.5 ~ loot template:corn_not_full
        playsound minecraft:block.weeping_vines.break block @a
        say harvest_not_full
    }

    function destroy {
        kill @e[type=minecraft:item_frame, tag=CornPlant, distance=..0.6]
        kill @e[type=interaction, distance=..0.6]
    }

    function break {
        say break
        execute as @s run say its meerer
        
        execute as @e[type=minecraft:item_frame, tag=CornPlant, nbt={Item:{id:"minecraft:item_frame", tag:{CustomModelData:34103}}}, distance=..0.5] run function template:corn/harvest_full
        execute as @e[type=minecraft:item_frame, tag=CornPlant, nbt={Item:{id:"minecraft:item_frame", tag:{CustomModelData:34102}}}, distance=..0.5] run function template:corn/harvest_not_full

        function template:corn/destroy
        # execute unless entity @e[tag=CornPlant,distance=..0.5] run setblock ~ ~ ~ air
        # function template:corn/get_seed
        # summon item ~ ~ ~ {Item:{id:"minecraft:item_frame",Count:1b,tag:{display:{Name:'{"text":"Corn Seed"}'},CustomModelData:34100,EntityTag:{Silent:1b,Tags:["CornPlant"],Item:{id:'minecraft:item_frame',Count:1b,tag:{CustomModelData:34102}},Invulnerable:1b,Invisible:1b,Fixed:1b}}}}
    }

    function get_seed {
        say get seed
        give @p item_frame{display:{Name:'{"text":"Corn Seed"}'}, CustomModelData:34101, EntityTag:{Tags:["CornPlant"],Item:{id:"minecraft:item_frame", Count:1b, tag:{CustomModelData:34102}}, Invulnerable:1b, Invisible:1b, Fixed:1b}} 1
    }

    function left {
        execute as @s run say left
        advancement revoke @s only template:left_click
        tag @s add this
        execute as @e[type=interaction,distance=..6] run function template:corn/find_attacked
        tag @s remove this
        say left2
    }

    function find_attacked {
        say find
        scoreboard players set #bool test 0
        execute on attacker store result score #bool test if entity @s[tag=this]
        execute if score #bool test matches 1 at @s run function template:corn/break
        execute if score #bool test matches 1 run data remove entity @s attack 
        say find2
    }

    function change_block {
        setblock ~ ~-1 ~ minecraft:stone replace
        # setblock ~ ~-1 ~ minecraft:farmland replace
    }

}

dir planter {
    function planter_tick {
        execute as @e[type=minecraft:glow_item_frame,tag=planter,tag=!placed] at @s run function template:planter/place
        execute as @e[type=minecraft:glow_item_frame,tag=planter,tag=placed] at @s unless block ~ ~ ~ minecraft:oak_planks run function template:planter/remove
    }

    function place {
        setblock ~ ~ ~ minecraft:oak_planks
        playsound minecraft:block.wood.place block @a[distance=0..5] ~ ~ ~ 1 1.2 1
        particle minecraft:cloud ~ ~ ~ 0 0 0 1 10
        tag @s add placed
    }
    
    function remove {
        playsound minecraft:block.wood.break block @a[distance=0..5] ~ ~ ~ 1 1.2 1

        summon item ~ ~0.5 ~ {Item:{id:"minecraft:glow_item_frame",Count:1b,tag:{display:{Name:'{"text":"Planter"}'},CustomModelData:34201,EntityTag:{Silent:1b,Tags:["planter"],Item:{id:"minecraft:glow_item_frame",Count:1b,tag:{CustomModelData:34201}},Invulnerable:1b,Invisible:1b,Fixed:1b}}}}
        # KILL BLOCK DROP
        kill @e[type=item,nbt={Item:{id:"minecraft:glass"}},distance=0..2,sort=nearest,limit=1]
        # KILL ITEM FRAME
        kill @s

        #execute at @p run fill ~1 ~1 ~1 ~-1 ~ ~-1 minecraft:iron_bars
        #execute at @p run fill ~ ~ ~ ~ ~1 ~ air
        #execute at @p run fill ~ ~20 ~ ~ ~15 ~ minecraft:anvil
    }

    function get_planter {
        summon item ~ ~0.5 ~ {Item:{id:"minecraft:glow_item_frame",Count:1b,tag:{display:{Name:'{"text":"Planter"}'},CustomModelData:34201,EntityTag:{Silent:1b,Tags:["planter"],Item:{id:"minecraft:glow_item_frame",Count:1b,tag:{CustomModelData:34201}},Invulnerable:1b,Invisible:1b,Fixed:1b}}}}
    }
}



