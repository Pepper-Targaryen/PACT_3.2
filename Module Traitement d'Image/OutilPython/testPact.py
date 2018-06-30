# -*- coding: utf-8 -*-


import argparse
from PIL import Image
from time import sleep
from random import randrange
import matplotlib.pyplot as plt
#im.show()

def arg_parser():
	parser = argparse.ArgumentParser(description=(
		'To get the image to process'))
	parser.add_argument('-f','--file',
			    required=True,
			    help='The file to be processed')
	parser.add_argument('-o','--output',
			    default='output',
			    help='Where the output skeleton will be stored')
	return parser.parse_args()
#args = arg_parser()
im = Image.open('planC2V2black.tiff')
#out=args.output

import numpy
imarray = numpy.array(im)

def afficherMat(matChar):
    for i in range(len(matChar)):
        ligne=""
        for j in range(len(matChar[i])):
            ligne +=" "*(len(str(matChar[i][j]))<2)+str(matChar[i][j])

        print(ligne)

plan = []
for i in range(im.size[0]):
    plan.append([])
    ligne = ""
    for j in range(im.size[1]):
        if  imarray[i][j][0]!= 255:
            plan[i].append("X")
        else:
            plan[i].append(" ")
#afficher(plan)


def afficher(liste,can): # liste est la liste des couleurs en hexa (Ou matrice)
    t=0.05
    for v in range(len(liste)) :
        for h in range(len(liste[0])): #on cree des carres, de gauche a droite, grace a l'incrementation de h
            can.create_rectangle(t*10+40*h*t,10*t+40*v*t, t*40*h+50*t, t*40*v+50*t, fill =liste[v][h],outline=liste[v][h])
    

def affCouleurs(matChar,coeff_affichage=15*16**3): #matChar est une matrice d'entiers que l'on veut afficher en couleurs
    
    t=0.4 #facteur de taille, chaque square est de taille proportionnelle a t
    res = []
    for i in range(len(matChar)):
        l = matChar[i]
        res.append([]) #matrice des hexa
        for k in range(len(l)):
            if l[k]=="X":
                res[i][k] =0
            truc = hex( int(l[k])*coeff_affichage)[2:] #changer le coeff ici selon le cas: on convertit chaque l[k]*coeff en hexa RVB (RGB)
            while len(truc)<6:
                truc = "0"+truc #Si l'on a quelque chose de trop court, rajoute des 0 au debut
            res[i].append( "#"+truc  )
    
    fen = Tk() #creation de la fenetre
    can = Canvas(fen, width =4*len(matChar[0])*10*t +2, height =4*len(matChar)*10*t+2) #cannevas accueillant le tableau
    can.grid(row = 0, columnspan = 5, padx =20, pady =20)  
    afficher(res,can)
    fen.mainloop()
    
    
        

def chanfrein(matChar):
    if matChar == []:
        print("Chanfrein d'une liste vide impossible")
    lh,lv=len(matChar[0]),len(matChar)
    def exists(i,j):
        return i>=0 and j>=0 and i<lh and j<lv
    res = []
    for i in range(lh):
        res.append([])
        for j in range(lv):
            if matChar[i][j] == "X":
                res[i].append(0)
            else:
                if exists(i-1,j) and exists(i,j-1):
                    min = 1+ res[i-1][j]*(res[i-1][j]<=res[i][j-1]) + res[i][j-1]*(res[i-1][j]>res[i][j-1])
                elif exists(i-1,j):
                    min = res[i-1][j]+1
                elif exists(i,j-1):
                    min = res[i][j-1]+1
                else:
                    min = 99
                res[i].append(min)
    for i in range(lh-1,-1,-1):
        for j in range(lv-1,-1,-1):
            if matChar[i][j] != "X":
                if exists(i+1,j) and exists(i,j+1):
                    min = 1+ res[i+1][j]*(res[i+1][j]<=res[i][j+1]) + res[i][j+1]*(res[i+1][j]>res[i][j+1])
                elif exists(i+1,j):
                    min = res[i+1][j]+1
                elif exists(i,j+1):
                    min = res[i][j+1]+1
                res[i][j]= min*(res[i][j]>=min) + res[i][j]*(res[i][j]<min)
    return res

#afficherMat(chanfrein(plan))
                
def squelette(matChar): #methode 1: comparaison aux voisins
    lh,lv=len(matChar[0]),len(matChar)
    def exists(i,j):
        return i>=0 and j>=0 and i<lh and j<lv
    chanf = chanfrein(matChar)
    res = []
    for i in range(lv):
        res.append([])
        for j in range(lh):
            count = 0
            voisins = [(i-1,j-1),(i,j-1),(i+1,j-1),(i-1,j),(i+1,j),(i-1,j+1),(i,j+1),(i+1,j+1)]
            for k in range(8):
                ip,jp=voisins[k]
                if exists(ip,jp) and chanf[i][j]>=chanf[ip][jp]:
                    count+=1
            if count >= 6 and chanf[i][j] !=0:
                res[i].append("1")
            else:
                res[i].append("0")
    return res

#afficherMat(chanfrein(plan))
#affCouleurs(chanfrein(plan),16**3)
#affCouleurs(squelette(plan))

            
def getSquelette(): #methode 2: reconnaissances de patterns      
    # Generate the squelette with the matrix
    #Matrix of Champer Distance
    matChanfrein = chanfrein(plan)
    #List of judgement, whether it can be eliminated
    topoJudgement = list('0011001111011101110011110000000100000011110111011100111100000001110011000000000000000000000000001100110011011101000000000000000100110011110111011100111100000001001100111101110111001111000000011100110000000000110011110000000111001100110111011100111111011111')
    #get thi-1,je biggest value of Matrix of Champer Distance
    import copy
    maxValue = 0
    matCodage = copy.deepcopy(matChanfrein)
    
    for i in range(len(matChanfrein)):
        for j in range(len(matChanfrein[0])):
            matCodage[i][j] = 0
            if maxValue < matChanfrein[i][j]:
                maxValue = matChanfrein[i][j]
    #try to eliminate
    for value in range(maxValue, 0, -1):
        for i in range(len(matChanfrein)):
            for j in range(len(matChanfrein[0])):
                if int(matChanfrein[i][j])==value:
                    matCodage[i][j] = 1
        if(value != maxValue):
            for i in range(len(matChanfrein)):
                for j in range(len(matChanfrein[0])):           
                    if int(matChanfrein[i][j])==value:
                        codage = str(matCodage[i-1][j-1])+str(matCodage[i-1][j])+str(matCodage[i-1][j+1])+str(matCodage[i][j-1])+str(matCodage[i][j+1])+str(matCodage[i+1][j-1])+str(matCodage[i+1][j])+str(matCodage[i+1][j+1])
                        code2int = int(codage, 2)
                        if(topoJudgement[code2int] == '1'):
                            matCodage[i][j] = 0
    affCouleurs(matCodage)

#getSquelette()

squ = squelette(plan)

#Il existe des parasites: on ne veut garder que la structure principale:
    
def suppParasites(squelette):
    import copy
    list = copy.deepcopy(squelette)
    lh,lv=len(squelette[0]),len(squelette)
    def exists(i,j):
        return i>=0 and j>=0 and i<lh and j<lv
    def voisins(i,j): #selectionne uniquement les voisins de valeur 1
        l = []
        poss = [(i-1,j),(i,j-1),(i+1,j),(i,j+1),(i-1,j-1),(i+1,j-1),(i-1,j+1),(i+1,j+1)] #voisins possibles (ajouter les diagonales?)
        for duo in poss:
            p,q = duo
            if exists(p,q) and list[p][q]=="1": #attention c'est des str, le ==1 ne marche pas
                l.append((p,q))
        return l
    id = 2
    poids = [0,0]
    for i in range(len(list)):
        for j in range(len(list[0])):
            if list[i][j]=='1':
                list[i][j]=id
                A = []
                A += voisins(i,j)
                n=1
                while A != []:
                    p,q = A.pop()
                    list[p][q] = id
                    A += voisins(p,q)
                    n+=1                
                id +=1
                poids.append(n)
    
    #On cherche la composante la plus grande: c'est celle avec l'id le plus commun, donc de poids le plus grand
    max = 0,0 #sup et id du sup
    for i in range(2,len(poids)):
        if poids[i]>max[0]:
            max = poids[i],i
    bestid = max[1]
    for i in range(len(list)):
        for j in range(len(list[0])):
            if list[i][j]==bestid:
                list[i][j]=1
            else:
                list[i][j]=0
    return list

squ2 = suppParasites(squ)  

def skeletonization():
    from skimage import img_as_bool, io, color, morphology
    
    image = img_as_bool(color.rgb2gray(io.imread('planC2V2black.tiff')))
    out = morphology.medial_axis(image)
    
    f, (ax0, ax1) = plt.subplots(1, 2)
    ax0.imshow(image, cmap='gray', interpolation='nearest')
    ax1.imshow(out, cmap='gray', interpolation='nearest')
    
    DefaultSize = plt.get_size_inches()
    plt.set_figsize_inches( (DefaultSize[0]*2, DefaultSize[1]*2) )
    plt.savefig("test.png", dpi = (1000))
    plt.show()
    
    res = []
    
    for i in range(len(out)):
        res.append([])
        for j in range(len(out[0])):
            if out[i][j]:
                res[i].append('1')
                
            else:
                if imarray[i][j][0] == 255: ##cette partie a ete modifiee pour garder les murs en memoire
                    res[i].append('2')
                else:
                    res[i].append('0')
        
    #print(res)
    #affCouleurs(suppParasites(res))
    return res
    
squ3 = skeletonization()

with open('output.txt', "w") as text_file:

    for i in range(len(squ3)):
        for j in range(len(squ3[0])):
            text_file.write("%d" %int(squ3[i][j]))
        text_file.write("\n")