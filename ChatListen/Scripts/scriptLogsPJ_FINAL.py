#####################################################################################################################
#                                           Bibliotecas utilizadas                                                  #
#####################################################################################################################
from __future__ import print_function
import MySQLdb
from bs4 import BeautifulSoup as BS # bibliotecas não nativas: urrlib2, beatifulsoup e mysql
from datetime import date, datetime, timedelta
import re, urllib2, time


#####################################################################################################################
#                            Funções criadas                                                                        #
#####################################################################################################################
def removeTags(dados):
    p = re.compile(r'<.*?>')    # funcão criada para remover todas tags html - tirada do stackoverflow  <*>
    return p.sub('', dados)

def removeParenteses(dados):
    p = re.compile(r'\(.*?\)')  # funcão criada para remover tudo entre e inclusive parenteses (*)
    return p.sub('', dados)

def removeParenteses2(dados):
    p = re.compile(r'\(.*?\]')  # funcão criada para remover tudo entre e inclusive parenteses e colchete (*]
    return p.sub('', dados)

def removeColchetes(dados):
    p = re.compile(r'\[.*?\]')  # funcão criada para remover tudo entre e inclusive colchetes [*]
    return p.sub('', dados)

def removeHoras(dados):
    p =re.compile(r'\d{1,2}:\d{1,2}:\d{1,2}....') # função criada para remover as RE nesta forma --:--:-- xx
    return p.sub('',dados)

def removeDatas(dados):
    p =re.compile(r'\d{1,2}\/\d{1,2}\/\d{2,4}') # função criada para remover datas
    return p.sub('',dados)

def removeDatas2(dados):
    p =re.compile(r'\d{1,2}-\d{1,2}-\d{2,4}') # função criada para remover datas
    return p.sub('',dados)

def saida():
    print("\n\n ---- apagando arquivos ----")
    arqSaida.truncate()
    arqSaida.close()

ti = time.time() # tempo inicial - pra marcar a duração da raspagem

#####################################################################################################################
#                            Variáveis usadas                                                                       #
#####################################################################################################################
i = 0
id = 1
aux1 = ''
aux2 = ''
aux3 = ''
aux4 = []
aux5 = ''
aux_tag = ''
nomes = []
lista = []
listaNomes = []
teste = []
caminho = 'C:\\Users\\theone\\Documents\\FATEC\\PROJETO TG1\\PJ_FINAL\\'
arqNomes = open(caminho + 'arqNomes.txt','r+')
arqNomes.truncate()

###################################################################################################################
#                           Baixa página para extração dos nomes dos predadores                                   #
###################################################################################################################

htmlNomes = urllib2.urlopen("http://www.perverted-justice.com/?con=full")     # Nesta pagina encontra-se a lista
soupNomes = BS(htmlNomes.read())                              # como todos os predadores condena  
                                                                              # nados com ajuda do PJ - 592 total 
for ahref in soupNomes.findAll('a',attrs={'id':'pedoLink'}):
    for nome in ahref:          # Neste loop os nomes sao descobertos e                              
        nomes.append(nome)      # armazenados no arquivo arqNomes.txt                                 

listaNomes = list(set(nomes))      # Remove duplicados

tamanho = len(listaNomes)          # Calculo tamanho da lista

for nome in listaNomes:                 # Escreve a lista dos predadores no arquivo
    arqNomes.write(nome+"\n")

#####################################################################################################################
#            Baixa os todos os logs, limpa um a um e gera um arquivo texto para cada log baixado e limpo            #
#####################################################################################################################
##print( " --- Baixando o log dos predatores - total de ", str(tamanho), " predadores ---\n")
##while (i < 592):
##    arqLogsPred = open('arqLogsPred-'+str(i)+'.txt','w')
##    arqLogsVit = open('arqLogsVit-'+str(i)+'.txt','w')
##    arqLogs = open('arqLogs-'+str(i)+'.txt','w')
##    arqLogs.truncate()
##    htmlLogs = urllib2.urlopen("http://www.perverted-justice.com/index.php?archive="+listaNomes[i]+"&nocomm=true")
##    soupLogs = BS(htmlLogs.read())
##    print(str(i), " - ", str(listaNomes[i]))
##
##    for code in soupLogs.find_all('span',attrs={'class':'code_c'}):
##        code.replace_with("")
##
##    for chat in soupLogs.find_all('div',attrs={'class':'chatLog'}):
##        chat.contents[0].replace_with("")
##        try:
##            aux_tag = removeTags(str(chat))    # problemas em decodificar unicode
##        except:
##            continue
##        aux1 = removeParenteses(aux_tag)
##        aux2 = removeParenteses2(aux1)
##        aux3 = removeColchetes(aux2)                     # return CR -> alguns arquivos com blueBold deram muito trabalho!!!
##        aux4 = removeHoras(removeDatas(removeDatas2(aux3))).split('\r')
##        for linha in aux4:
##            if re.match(r'[\r]',linha):
##                continue
##            if listaNomes[i] in linha:
##                aux5 = linha[linha.find(':')+2:]
##                print(aux5,file=arqLogsPred)
##            else:
##                aux5 = linha[linha.find(':')+2:]
##                print(aux5,file=arqLogsVit)
##            print(aux5)
##            print(linha,file=arqLogs, end='')
##
##    i += 1

#####################################################################################################################
#                            Lê cada arquivo e grava novamente a fim de limpar ainda mais                           #
#####################################################################################################################
##i = 0
##while(i < 592):
##    arq = open('arqLogs-'+str(i)+'.txt','r+')
##    arqFinal = open('arqLogsFinal-'+str(i)+'.txt','w')
##    for linha in arq:
##        if re.match(r'^[\r,\n,\r\n]',linha):
##            continue
##        print(linha, file=arqFinal, end='\n')
##    i += 1

#####################################################################################################################
#    Edita cada arquivo de log local inserindo id, tabs e identificador de tipo e grava todas as linhas editadas    #
#                                        em um único arquivão (arq.txt)                                             #
#####################################################################################################################
##arqSaida = open(caminho + 'arqSemNumSemTipo.txt', 'a')
##arqSaida.truncate()
##while(i < 592):
##    arqEntrada = open(caminho + 'arqLogsFinal-' + str(i) + '.txt','r')
##    for linha in arqEntrada:
##        if str(listaNomes[i]) in linha:
##            arqSaida.write(linha[0:linha.find(':') - 1:] + '\t' + linha[linha.find(':') + 2::])
##        else:
##            arqSaida.write(linha[0:linha.find(':') - 1:] + '\t' + linha[linha.find(':') + 2::])
##        teste.append(len(linha))
##        id += 1;
##    i += 1
##
##tam = max(teste)
##print(tam)

#####################################################################################################################

arqCateg = open(caminho + 'arqCateg.txt','a')
arqCateg.truncate()
arqBD = open(caminho + 'arqBD.txt','a')
arqBD.truncate()
arqLogRawPred = open(caminho + 'arqRawPred.txt','a')
arqBD.truncate()
i = 0
id = 1
while(i <= 591):
    arqPred = open(caminho + 'arqPredBD-' + str(i) + '.txt', 'a')
    arqPred.truncate()
    arqVit = open(caminho + 'arqVitBD-' + str(i) + '.txt', 'a')
    arqVit.truncate()
    arqLogRawPred = open(caminho + 'arqLogRawPred-' + str(i) + '.txt', 'a')
##    arqTemp.truncate()
    arqEntrada = open(caminho + 'arqLogsFinal-' + str(i) + '.txt','r')
    for linha in arqEntrada:
        try:
            teste = linha[0:linha.index(': \n')] # remove as linhas sem texto (somente nome : )
            continue
        except:
            pass

        try:
            teste = linha[0:linha.index(':\n')] # remove as linhas sem texto (somente nome : )
            continue
        except:
            pass

        try:
            teste = linha[0:linha.index(' :\n')] # remove as linhas sem texto (somente nome : )
            continue
        except:
            pass

        try:
            teste = linha[0:linha.index('  :\n')] # remove as linhas sem texto (somente nome : )
            continue
        except:
            pass

        try:
            teste = linha[0:linha.index(':  \n')] # remove as linhas sem texto (somente nome : )
            continue
        except:
            pass

        try:
            teste = linha[0:linha.index(':   \n')] # remove as linhas sem texto (somente nome : )
            continue
        except:
            pass

        try:
            teste = linha[0:linha.index(':     \n')] # remove as linhas sem texto (somente nome : )
            continue
        except:
            pass

        try:
            teste = linha[0:linha.index(':\t\n')] # remove as linhas sem texto (somente nome : )
            continue
        except:
            pass

        linha = linha.replace(':  ', ' : ')
        linha = linha.replace(': ', ':')
        linha = linha.rstrip() + '\n'                

        if str(listaNomes[i]) in linha:
            try:
##                arqPred.write(str(1) + '\t' + linha[linha.index(':') + 1::])
                arqLogRawPred.write(linha[linha.index(':') + 1::])
##                arqRawPred.write(linha[linha.index(':') + 1::])
##                arqBD.write(str(id) + '\t' + linha[linha.index(':') + 1::])
##                arqCateg.write(str(1) + '\t' + linha[linha.index(':') + 1::])
                id += 1
            except:
                pass
        else:
            try:
                pass
##                arqVit.write(str(0) + '\t' + linha[linha.index(':') + 1::])
##                arqTemp.write(str(0) + '\t' + linha[linha.index(':') + 1::])
##                arqCateg.write(str(0) + '\t' + linha[linha.index(':') + 1::])
            except:
                pass
    print(str(i))
    i += 1
##    arqTemp.close()

##arqSaida = open(caminho + 'arqSaida.txt','a')
##arqSaida.truncate()

##arqTemp = open(caminho + 'arqBD.txt','r')
##for linha in arqTemp:
##    arqSaida.write(linha)
##arqSaida.close()
##arqTemp.close()

##arqRawPred.close()

##drop = ("drop table if exists chatonlp")
##creation = ('''
##                create table if not exists chatonlp
##                    (
##                        id int not null auto_increment primary key,
##                        frase varchar(1260)
##                    )
##        ''')
##trunc = ("TRUNCATE chatonlp")
##load = ("LOAD DATA LOCAL INFILE 'C:/users/theone/Documents/FATEC/PROJETO TG1/PJ_FINAL/arqSaida.txt' INTO TABLE chatonlp")
##
##try:
##    conexao = MySQLdb.Connect(host='127.0.0.1', user='root', passwd='741555', db='fbchatwordlog')
##    cursor = conexao.cursor()
##except:
##    print("Falha Conexão\n")
##
##try:
##    cursor.execute(drop)
##    cursor.execute(creation)
##    cursor.execute(trunc)
##    cursor.execute(load)
##    conexao.commit()
##finally:
##    print("Falhou DDL e DML")
##    conexao.close()
 
    
#####################################################################################################################

#####################################################################################################################
#                            Cria a tabela e a popula com as linhas do arq.txt                                      #
#####################################################################################################################
##drop = ("drop table if exists chatarqtotal")
##creation = ('''
##                create table if not exists chatarqtotal
##                    (
##                        id int not null auto_increment primary key,
##                        tipo varchar(1),
##                        nome varchar(50),
##                        frase varchar(''' + str(tam + 30) + ''')
##                    )
##        ''')
##trunc = ("TRUNCATE chatonlp")
##load = ("LOAD DATA LOCAL INFILE 'C:/sers/theone/Documents/FATEC/PROJETO TG1/PJ_FINAL/arqSemNumSemTipo.txt' INTO TABLE chatonlp")
##try:
##    conexao = MySQLdb.Connect(host='127.0.0.1', user='root', passwd='741555', db='fbchatwordlog')
##    cursor = conexao.cursor()
##except:
##    print("Falha Conexão\n")
##
##try:
##    cursor.execute(drop)
##    cursor.execute(creation)
##    cursor.execute(load)
##    conexao.commit()
##finally:
##    print("Falhou DDL e DML")
##    conexao.close()
##         
#######################################################################################################################
###                                          Cálculo de tempo de execução                                             #
#####################################################################################################################
tf = time.time()  # tempo final da raspagem
t = int(tf - ti)
horas = t / 3600
minutos = (t - horas*3600) / 60
segundos = t - horas*3600 - minutos*60
print("\nTempo decorrido: ", str(horas)+"h"+str(minutos)+"min"+str(segundos)+"s")
