# -*- coding: utf-8 -*-
"""
Created on Mon Jan 29 14:22:19 2018

@author: yfei
"""
#get a library of judgement
flagSet = []

#For each instance, judge whether it can be eliminated
for i in range(256):
    print("line%d:" %i)
    str = '{0:08b}'.format(i)
    print(str[0]+str[1]+str[2]) 
    print(str[3]+' '+str[4])
    print(str[5]+str[6]+str[7])
    flag = input('Yes? ')
    print
    flagSet.append(flag)

#Write the result to a file named as Output.txt
with open("Output.txt", "w") as text_file:

    for i in range(256):
        text_file.write("%d" %flagSet[i])

    
