import pandas as pd
p = pd.read_csv('C:\\d\\vysehrad\\nasa-scraper\\src\\test\\resources\\testing-planets.csv', sep=';')
print(p)
p.T.to_csv('testing-planets_output.csv', header=False)
