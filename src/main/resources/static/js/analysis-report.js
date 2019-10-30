
class Reports {
  static saveReport () {
	let reportHallmarks= window.localStorage.getItem('reportHallmarks');
	if(reportHallmarks==null||reportHallmarks==undefined){
		alert("You do not have any analysis hallmark, please add at least one to generate a report.")
		return;
	}
	reportHallmarks = JSON.parse(reportHallmarks);
    let pdf = new jsPDF('p', 'cm', 'a4', true)
	reportHallmarks.forEach(function(element){
		pdf.text(20, 20, element.hallmark);
		pdf.addSvg(element.img, 40, 40, 200, 400);

	}) 
	pdf.save('analysis-report.pdf');
	window.localStorage.removeItem('reportHallmarks');


  }
}

