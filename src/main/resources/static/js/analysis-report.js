
class Reports {
  static saveReport () {
	let reportHallmarks= window.localStorage.getItem('reportHallmarks');
	if(reportHallmarks==null||reportHallmarks==undefined){
		alert("You do not have any analysis hallmark, please add at least one to generate a report.")
		return;
	}
	reportHallmarks = JSON.parse(reportHallmarks);
    let pdf = new jsPDF('p', 'cm', 'a4', true)
    var canvas = document.createElement('canvas');
  
	reportHallmarks.forEach(function(element, index){
		pdf.setFontSize(18);
		pdf.text(2, 2, (index+1)  + ". Hallmark description:");
		pdf.setFontSize(12)
		var splitTitle = pdf.splitTextToSize(element.hallmark, 40);
		pdf.text(2, 3, splitTitle);
		canvg(canvas, element.img);
		var imgData = canvas.toDataURL('image/jpeg');
		pdf.addImage(imgData, 'JPEG', 0.5, 6, 20, 9);
		pdf.addPage();

	}) 
	pdf.deletePage(reportHallmarks.length+1);
	pdf.save('analysis-report.pdf');
	window.localStorage.removeItem('reportHallmarks');


  }
}

