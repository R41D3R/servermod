#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s run say left
advancement revoke @s only template:left_click
tag @s add this
execute as @e[type=interaction,distance=..6] run function template:corn/find_attacked
tag @s remove this
say left2