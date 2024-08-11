#built using mc-build (https://github.com/mc-build/mc-build)

say find
scoreboard players set #bool test 0
execute on attacker store result score #bool test if entity @s[tag=this]
execute if score #bool test matches 1 at @s run function template:corn/break
execute if score #bool test matches 1 run data remove entity @s attack
say find2