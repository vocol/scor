	
# SCORVoc

The [Supply Chain Operation Reference (SCOR)](http://www.apics.org/sites/apics-supply-chain-council/frameworks/scor) is a cross-industry approach to lay the groundwork for more efficient and effective information exchange in supply networks.
SCORVoc is an OWL vocabulary which fully formalizes the latest SCOR standard, while overcoming identified limitations of existing formalizations.

## Overview Diagram

![alt tag](https://raw.githubusercontent.com/vocol/scor/master/diagram.png)


## Example Process

```
scor:exampleProcess a        		   scor:DeliverStockedProduct ;
			        :hasCommitDate     "2015-11-05" ;
			        :hasSupplier       "Sangio Sugar Factory" ;
			        :product           "Raw sugar" ;
			        :processId         "2ad9380f-1eb4-4595-857b-1bcf874621bb" ;

			        scor:hasMetricRL_33 "90" ;  # Delivery Item Accuracy
			        scor:hasMetricRL_34 "100" . # Delivery Location Accuracy
```


## Accessibility

Preferred prefix identifier: [`scor`](http://prefix.cc/scor)
Vocabulary namespace: [`http://purl.org/eis/vocab/scor#`](http://purl.org/eis/vocab/scor#)

## Test Dataset

See /generator for more information

## Future

SCORVoc is planned as the basis for the [SCMApp](https://github.com/np00/scmApp). SCMApp provides a Web Interface Configurator to manage Supply Chains. 

-
© APICS 2015. APICS, CCOR, CPIM, CSCP, DCOR, SCOR, and SCORmark are all registered trademarks of APICS.  All rights reserved.
