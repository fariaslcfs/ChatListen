from __future__ import print_function
import urllib2, time, wget

ti = time.time() # tempo inicial - pra marcar a duração da raspagem

caminho = 'C:\\Users\\theone\\Desktop\\PJ_RASCUNHO\\'

arqPics = open(caminho + "arqPictures.txt", "r+")
for nome in arqPics:
    nome = nome.strip()
    link = "http://www.perverted-justice.com/pedopics/"+nome     # Nesta pagina encontra-se a lista
    arqFoto = wget.download(link)

tf = time.time()
t = int(tf - ti)
horas = t / 3600
minutos = (t - horas*3600) / 60
segundos = t - horas*3600 - minutos*60
print("\nTempo decorrido: ", str(horas)+"h"+str(minutos)+"min"+str(segundos)+"s")
