
function modificationLocalisation(){	
	var modal =$('#modalModifierLocalisation')
	$(modal).modal('show');
}
	
	$(document).ready(function () {
		
		var modal = $(this)
		
		let checkboxCommune = document.getElementById("checkboxCommune");
		let checkboxCommuneArrond = document.getElementById("checkboxCommuneArrond");
		let checkboxDepartement = document.getElementById("checkboxDepartement");
		let checkboxRegion = document.getElementById("checkboxRegion");
		let inputCommuneModifier = document.getElementById("commune");
		let inputCommuneArrondModifier = document.getElementById("communeArrond");
		let inputDepartementModifier = document.getElementById("departement");
		let inputRegionModifier = document.getElementById("region");
		
	        checkboxCommune.addEventListener( "change", () => {
	        		if ( checkboxCommune.checked == true)
	        			{ 	
	        				document.getElementById("nouvelleCommune").style.display='block';	
	        				
	        			}
	        		if ( checkboxCommune.checked == false)
        			{ 	
        				document.getElementById("nouvelleCommune").style.display='none';	
        			}
	        });
	        
	        inputCommuneModifier.addEventListener( "change", () => {
	        	document.getElementById("communeModifier").value=$("#commune").val();
        });
	        
	        
	        		
	        		checkboxCommuneArrond.addEventListener( "change", () => {
		        		if ( checkboxCommuneArrond.checked == true)
		        			{ 	
		        				document.getElementById("nouvelleCommuneArrond").style.display='block';	
		        			}
		        		if ( checkboxCommuneArrond.checked == false)
	        			{ 	
	        				document.getElementById("nouvelleCommuneArrond").style.display='none';	
	        			}
	        });
	        		
	        		 inputCommuneArrondModifier.addEventListener( "change", () => {
	     	        	document.getElementById("communeArrondModifier").value=$("#communeArrond").val();
	             });
	        		
	        		checkboxDepartement.addEventListener( "change", () => {
		        		if ( checkboxDepartement.checked == true)
		        			{ 	
		        				document.getElementById("nouveauDepartement").style.display='block';	
		        			}
		        		if ( checkboxDepartement.checked == false)
	        			{ 	
	        				document.getElementById("nouveauDepartement").style.display='none';	
	        			}
		        });
	        		
	        		 inputDepartementModifier.addEventListener( "change", () => {
	     	        	document.getElementById("departementModifier").value=$("#departement").val();
	             });
	        		
	        		checkboxRegion.addEventListener( "change", () => {
		        		if ( checkboxRegion.checked == true)
		        			{ 	
		        				document.getElementById("nouvelleRegion").style.display='block';	
		        			}
		        		if ( checkboxRegion.checked == false)
	        			{ 	
	        				document.getElementById("nouvelleRegion").style.display='none';	
	        			}
		        });
	        		
	        		 inputRegionModifier.addEventListener( "change", () => {
	     	        	document.getElementById("regionModifier").value=$("#region").val();
	             });
	        		 	
	   });
	       

