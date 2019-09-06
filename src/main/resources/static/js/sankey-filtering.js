	var expandAndCollapse = function(){
			$(".fa-expand-arrows-alt").click(function(event){
				var selectedFeatures = getSelectedFeatures();
				var urlString;
				if($(this).attr("data-type").toUpperCase()=="PRODUCT"){
				    urlString = "/expand/product/"+$(this).attr("data-id")+"?features="+selectedFeatures;
				}else if($(this).attr("data-type").toUpperCase()=="PRODUCT"){
					//CAMBIAR POR PARENT FEATURE
				    urlString = "/expand/parent-feature/"+$(this).attr("data-id")+"?features="+selectedFeatures;
				}else{
					urlString = "/nodes/"+$(this).attr("data-id")+"?features="+selectedFeatures
				}
		       	$.ajax({
		       		type: 'POST',
			       	url : urlString,
		    		data : JSON.stringify({"sankeyItems" : linksData, "nodes" : nodes}),
		    		dataType: "json",
		    		contentType:"application/json",
			       	accept: "application/json",
		     		success: function(data) {
		       			linksData = data.sankeyItems;
		       			nodes = data.nodes;
		       			sankeyChart.update({
			       			 plotOptions: {
			     				series:{
			     					animation:{
			       		        		duration: 2000
			       		        	}
			       				}
			       			 },
		       		        series: {
			       		            data: data.sankeyItems,
			       		            nodes: data.nodes,
			       		         	
		       		        }
	       				});
		       			expandAndCollapse();
			            }
			       		
			       	});
					
		       	event.stopPropagation();
			    event.preventDefault();
			});   	
	       	$(".fa-minus-square").click(function(event){
				var selectedFeatures = getSelectedFeatures();
		       	$.ajax({
		       		type: 'POST',
		       		url : "/collapse/"+$(this).attr("data-id")+"?features="+selectedFeatures,
		       		data : JSON.stringify({"sankeyItems" : linksData, "nodes" : nodes}),
		       		dataType: "json",
		       		contentType:"application/json",
		       		accept: "application/json",
		       		success: function(data) {
		       			linksData = data.sankeyItems;
		       			nodes = data.nodes;
		       			sankeyChart.update({
		       			 plotOptions: {
		     				series:{
		     					animation:{
		       		        		duration: 2000
		       		        			  }
		       					   }
		       				 },
		       		        series: {
		       		            data: data.sankeyItems,
		       		            nodes: data.nodes,
		       		         	
		       		        }
	       				});
		       			expandAndCollapse();
		            }
		       		
	       		});   	
		       	event.stopPropagation();
			    event.preventDefault(); 	
			});
		}
		expandAndCollapse();
		
	      
	      
	      function filterFunction(myinput,mydropdown) {
			  var input, filter, ul, li, a, i;
			  input = document.getElementById(myinput);
			  filter = input.value.toUpperCase();
			  ul = document.getElementById(mydropdown);
			  a = ul.getElementsByTagName("li");
			  for (i = 1; i < a.length; i++) {
			    txtValue = a[i].getElementsByTagName('label')[0].textContent || a[i].getElementsByTagName('label')[0].innerText;
			    if (txtValue.toUpperCase().indexOf(filter) > -1) {
			      a[i].style.display = "";
			    } else {
			      a[i].style.display = "none";
			    }
			  }
			}
	      
			function getSelectedFeatures(){
		
				var featuresCheckBoxes = document.getElementsByClassName("feature-checkbox");
				var selectedFeatures = [];
				for (var i = 0; i < featuresCheckBoxes.length; i++) {
					  if(featuresCheckBoxes[i].checked){
						  selectedFeatures.push(featuresCheckBoxes[i].getAttribute("data-id"));
					  }
				}
				return selectedFeatures;
			}
			
			function filterSelectedFeatures(){
				var selectedFeatures = getSelectedFeatures();
				if(selectedFeatures.length>0){
					$.ajax({
			       		type: 'POST',
			       		url : "/filter-features?features="+selectedFeatures,
			       		data : JSON.stringify(linksData),
			       		dataType: "json",
			       		contentType: "application/json",
			       		accept: "application/json",
			       		success: function(data) {
			       			linksData = data.sankeyItems;
			       			nodes = data.nodes;
			       			sankeyChart.update({
			       			 plotOptions: {
			     				series:{
			     					animation:{
			       		        		duration: 2000
			       		        			  }
			       					   }
			       				 },
			       		        series: {
			       		            data: data.sankeyItems,
			       		            nodes: data.nodes,
			       		         	
			       		        }
		       				});
			       			expandAndCollapse();
			            }
			       		
		       		});  
				
				} 	
				
				
			}
			
			
			function clearFeatureFilters(){
					$.ajax({
			       		type: 'GET',
			       		url : "/clear-feature-filters",
			       		accept: "application/json",
			       		success: function(data) {
			       			linksData = data.sankeyItems;
			       			nodes = data.nodes;
			       			sankeyChart.update({
			       			 plotOptions: {
			     				series:{
			     					animation:{
			       		        		duration: 2000
			       		        			  }
			       					   }
			       				 },
			       		        series: {
			       		            data: data.sankeyItems,
			       		            nodes: data.nodes,
			       		         	
			       		        }
		       				});
			       			expandAndCollapse();
			            }
			       		
		       		});  
					 
					var featuresCheckBoxes = document.getElementsByClassName("feature-checkbox");
					var selectedFeatures = [];
					for (var i = 0; i < featuresCheckBoxes.length; i++) {
						  featuresCheckBoxes[i].checked = false;
						  
					}
				
			}
			
			
			function filterSelectedPackages(){
				var packagesCheckBoxes = document.getElementsByClassName("package-checkbox");
				var selectedPackages = [];
				for (var i = 0; i < packagesCheckBoxes.length; i++) {
					  if(packagesCheckBoxes[i].checked){
						  selectedPackages.push(packagesCheckBoxes[i].getAttribute("data-id"));
					  }
				}
				if(selectedPackages.length>0){
					var filteredNodes = nodes.filter(node => node.sankeyNodeType!="PACKAGE"|| selectedPackages.includes(node.id));
					var filteredLinks = linksData.filter(link => !(["PACKAGEPRODUCT","FEATUREPACKAGE"].includes(link.sankeyLinkType)) || selectedPackages.includes(link.from) ||selectedPackages.includes(link.to) );
					updateSankey(filteredLinks,filteredNodes);
				}else{
					updateSankey(linksData,nodes);
				}
			}
			
			
			function filterSelectedProducts(){
				var productsCheckBoxes = document.getElementsByClassName("product-checkbox");
				var selectedProducts = [];
				for (var i = 0; i < productsCheckBoxes.length; i++) {
					  if(productsCheckBoxes[i].checked){
						  selectedProducts.push(productsCheckBoxes[i].getAttribute("data-id"));
					  }
				}
				if(selectedProducts.length>0){
					var filteredNodes = nodes.filter(node => node.sankeyNodeType!="PRODUCT"|| selectedProducts.includes(node.id));
					var filteredLinks = linksData.filter(link => !(["PACKAGEPRODUCT","PRODUCTFEATURE"].includes(link.sankeyLinkType)) || selectedProducts.includes(link.from) ||selectedProducts.includes(link.to) );
					updateSankey(filteredLinks,filteredNodes);
				}else{
					updateSankey(linksData,nodes);
				}
			}
			
			function updateSankey(updatedData, updatedNodes){
				sankeyChart.update({
	       			 plotOptions: {
	     				series:{
	     					animation:{
	       		        		duration: 2000
	       		        	}
	       				}
	       			 },
	       		        series: {
	       		            data: updatedData,
	       		            nodes: updatedNodes,
	       		         	
	       		        }
      				});
				expandAndCollapse();
				
			}