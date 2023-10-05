//Chart.js scripts
//-- Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#292b2c';

//-- Bar Chart Example
var kTypeDocument = $("#kTypeDocument").val();
kTypeDocument = kTypeDocument.replace("[","").replace("]","").split(",");

var vTotalTypeDoc = $("#vTotalTypeDoc").val().trim();
vTotalTypeDoc = vTotalTypeDoc.replace("[","").replace("]","").split(",");
var ctx = document.getElementById("myBarChart");
var myLineChart = new Chart(ctx, {
type: 'bar',
data: {
 labels: kTypeDocument,
 datasets: [{
   label: "Revenue",
   backgroundColor: "rgba(2,117,216,1)",
   borderColor: "rgba(2,117,216,1)",
   data: vTotalTypeDoc,
 }],
},
options: {
 scales: {
   xAxes: [{
     time: {
       unit: 'month'
     },
     gridLines: {
       display: false
     },
     ticks: {
       maxTicksLimit: 6
     }
   }],
   yAxes: [{
     ticks: {
       min: 0,
       max: vTotalTypeDoc.max,
       maxTicksLimit: 5
     },
     gridLines: {
       display: true
     }
   }],
 },
 legend: {
   display: false
 }
}
});