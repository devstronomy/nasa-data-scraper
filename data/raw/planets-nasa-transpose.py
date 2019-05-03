import pandas as pd

p = pd.read_csv('planets-orig.csv', sep=';')
p.T.to_csv('planets.csv', header=False)
