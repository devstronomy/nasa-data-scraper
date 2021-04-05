import pandas as pd

# p = pd.read_csv('testing-planets.csv', sep=';')
p = pd.read_csv('C:\\d\\vysehrad\\nasa-scraper\\src\\test\\resources\\testing-planets.csv', sep=';')
# p = pd.read_csv("testing-planets.csv", sep=';')
# p = pd.read_csv('C:\d\vysehrad\nasa-scraper\src\test\resources\testing-planets.csv', sep=';')
p.T.to_csv('planets2.csv', header=False)
