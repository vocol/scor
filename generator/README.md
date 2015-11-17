	
# SCMDataGenerator

The SCMDataGenerator generate a supply chain dataset (Turtle) and measures the execution time of chosen SPARQL queries on that dataset.

## Possible Parameter

For setting the depth of the supply chain (default: 2):
 ```
-depth=<2-5>
 ```

For setting the industry of the supply chain (default: agriculture):
 ```
-industry={automotive, healthcare, computer, agriculture}
 ```
	
For setting the number of partners (default: 2):  
```
-partners=<2-5>
 ```

Number of triples which are generated (default: 100k):
```
-size=<100000, 500000, 1000000> 

 ```