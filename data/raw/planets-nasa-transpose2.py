import pandas as pd

# p = pd.read_csv('C:\d\vysehrad\nasa-scraper\data\raw\planets-nasa-export2.csv', sep=';')
p = pd.read_csv('../raw/planets-nasa-export2.csv', sep=';')
p.T.to_csv('../csv/planets2.csv', header=False)
