import pandas as pd
import string

fileNameFromScraper = 'C:\\d\\vysehrad\\fork-nasa-data-scraper\\nasa-data-scraper\\data\\raw\\planetsFromScraper.csv'
fileNameToPersister = 'C:\\d\\vysehrad\\fork-nasa-data-scraper\\nasa-data-scraper\\data\\csv\\planetsToPersister.csv'

f5=open(fileNameFromScraper,"r+")
input=f5.read()
input=input.replace(',','')

f6=open(fileNameFromScraper,"w+")
f6.write(input.title())

f5.close()
f6.close()



print('>>>>>>>>>>  Reading planetsFromScraper.csv')
p = pd.read_csv(fileNameFromScraper, sep=';')
print('>>>>>>>>>> Print p ')
print(p)



print('>>>>>>>>>>> Clear planetsToPersister.csv.csv')
filename = fileNameToPersister
# opening the file with w+ mode truncates the file
f = open(filename, "w+")
f.close()


print('>>>>>>>>>>> Writing to planetsToPersister.csv.csv')
p.T.to_csv(fileNameToPersister, header=False)
print('>>>>>>>>>>>  End transform python script')


f1=open(fileNameToPersister,"r+")
input=f1.read()
input=input.replace('"','')
input=input.replace('*','')
input=input.replace('Unknown','Unknown*')


f2=open(fileNameToPersister,"w+")
f2.write(input)

f1.close()
f2.close()



f3=open(fileNameToPersister,"r+")
input=f3.read()

f4=open(fileNameToPersister,"w+")
f4.write(input.title())


f3.close()
f4.close()
