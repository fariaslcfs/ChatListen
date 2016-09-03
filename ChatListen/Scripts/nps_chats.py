from nltk.corpus import nps_chat as nps

# NPS_CHAT can be found in: https://catalog.ldc.upenn.edu/LDC2010T05
# but is a charged service - buaa buaa buaa

caminho = 'C:\\Users\\theone\\Documents\\FATEC\\PROJETO TG1\\PJ_FINAL\\'
i = 0
for fid in nps.fileids():
    print('CREATING FILE: ' + 'arqNPS_CHAT-' + str(i) + '--' + fid[:-4] + '.txt\n')
    arq = open(caminho + 'arqNPS_CHAT-' + str(i) + '--' + fid[:-4] + '.txt', 'a')
    arq.truncate()
    for post in nps.posts(fid):
        line = ' '.join(post).rstrip()
        if 'ACTION' in line or 'JOIN' in line or 'PART' in line:
            continue
        arq.write(line + '\n')
    arq.close()
    i+=1
    
