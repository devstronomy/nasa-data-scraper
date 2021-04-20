import pandas as pd
import pathlib

fileNameFromScraper = 'planetsFromScraper.csv'
pathScraper = str(pathlib.Path(__file__).parent.absolute())
pathFileFromScraper = pathScraper + '/' + fileNameFromScraper
print('Absolute Path to file came from scraper: ' + pathFileFromScraper)


fileNameToPersister = 'planetsToPersister.csv'
pathPersister = str(pathlib.Path(__file__).parent.parent.absolute()) + "/csv"
pathFileToPersister = pathPersister + '/' + fileNameToPersister
print('Absolute Path to file to persister: ' + pathFileToPersister)


print('Polish the file from scraper: ' + pathFileFromScraper)
f5=open(pathFileFromScraper,"r+")
input=f5.read()
input=input.replace(',','')
f6=open(pathFileFromScraper,"w+")
f6.write(input.title())
f5.close()
f6.close()


print('Reading file from scraper ' + fileNameFromScraper)
p = pd.read_csv(pathFileFromScraper, sep=';')
print(p)


print('Clear file to persister: ' + fileNameToPersister)
path = pathFileToPersister
# opening the file with w+ mode truncates the file
f = open(path, "w+")
f.close()

print('Writing to file for persister: ' + fileNameToPersister)
p.T.to_csv(pathFileToPersister, header=False)
print('Transform file: ' + fileNameFromScraper + ' to file:  ' + fileNameToPersister + ' finished')


print('Polishing format of file: ' + fileNameToPersister)
f1=open(pathFileToPersister,"r+")
input=f1.read()
input=input.replace('"','')
input=input.replace('*','')
input=input.replace('Unknown','Unknown*')
f2=open(pathFileToPersister,"w+")
f2.write(input)
f1.close()
f2.close()

print(' ')
f3=open(pathFileToPersister,"r+")
input=f3.read()
f4=open(pathFileToPersister,"w+")
f4.write(input.title())
f3.close()
f4.close()