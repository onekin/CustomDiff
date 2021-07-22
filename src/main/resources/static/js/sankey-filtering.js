var expandAndCollapse = function () {
    $(".fa-plus-square").click(function (event) {
        var selectedFeatures = getSelectedFeatures();
        var urlString;
        if ($(this).attr("data-type").toUpperCase() == "PRODUCT") {
            urlString = "/expand/product/" + $(this).attr("data-id") + "?features=" + selectedFeatures;
        } else if ($(this).attr("data-type").toUpperCase() == "PARENTFEATURE") {
            // CAMBIAR POR PARENT FEATURE
            urlString = "/expand/parent-feature/" + $(this).attr("data-id") + "?features=" + selectedFeatures;
        } else if ($(this).attr("data-type").toUpperCase() == "FEATURE") {
            urlString = "/expand/feature/" + $(this).attr("data-id") + "?features=" + selectedFeatures
        } else if ($(this).attr("data-type").toUpperCase() == "LEFTPACKAGE") {
            urlString = "/expand/left-package/" + $(this).attr("data-id") + "?features=" + selectedFeatures
        } else if ($(this).attr("data-type").toUpperCase() == "RIGHTPACKAGE") {
            urlString = "/expand/right-package/" + $(this).attr("data-id") + "?features=" + selectedFeatures
        }

            $.ajax({
                type: 'POST',
                url: urlString,
                data: JSON.stringify({"sankeyLinks": linksData, "nodes": nodes}),
                dataType: "json",
                contentType: "application/json",
                accept: "application/json",
                success: function (data) {
                    linksData = data.sankeyLinks;
                    nodes = data.nodes;
                    sankeyChart.update({
                        series: {
                            data: data.sankeyLinks,
                            nodes: data.nodes,

                        }
                    });
                    expandAndCollapse();
                }

            });

            event.stopPropagation();
            event.preventDefault();
        }
    );
    $(".fa-minus-square").click(function (event) {
        var selectedFeatures = getSelectedFeatures();
        var urlString;
        if ($(this).attr("data-type").toUpperCase() == "LEFTASSET") {
            urlString = "/collapse/left-asset/" + $(this).attr("data-id") + "?features=" + selectedFeatures;
        } else if ($(this).attr("data-type").toUpperCase() == "FEATURE") {
            urlString = "/collapse/feature/" + $(this).attr("data-id") + "?features=" + selectedFeatures;
        } else if ($(this).attr("data-type").toUpperCase() == "RIGHTASSET") {
            urlString = "/collapse/right-asset/" + $(this).attr("data-id") + "?features=" + selectedFeatures
        }else if ($(this).attr("data-type").toUpperCase() == "FEATURESIBLING") {
            urlString = "/collapse/featuresibling/" + $(this).attr("data-id") + "?features=" + selectedFeatures
        }
        $.ajax({
            type: 'POST',
            url: urlString,
            data: JSON.stringify({"sankeyLinks": linksData, "nodes": nodes}),
            dataType: "json",
            contentType: "application/json",
            accept: "application/json",
            success: function (data) {
                linksData = data.sankeyLinks;
                nodes = data.nodes;
                sankeyChart.update({
                    series: {
                        data: data.sankeyLinks,
                        nodes: data.nodes,

                    },
                    sankey: {
                        nodePadding: 25,
                        dataLabels: {
                            style: {
                                color: 'black',


                            },
                            useHTML: true,
                            enabled: true,
                            nodeFormatter: function () {
                                if (this.point.expandable == true && this.point.collapsable == true) {
                                    return this.point.name + '<i class="far fa-plus-square" rel="tooltip" title="Expand this package" data-id="' + this.point.id + '" data-type="' + this.point.sankeyNodeType + '"></i>' +
                                        '<i class="far fa-minus-square" rel="tooltip" title="Collapse package files" data-id="' + this.point.parentId + '" data-type="' + this.point.sankeyNodeType + '"></i>';
                                } else if (this.point.expandable == true) {
                                    return this.point.name + '<i class="far fa-plus-square" rel="tooltip" title="Expand this package" data-id="' + this.point.id + '" data-type="' + this.point.sankeyNodeType + '"></i>';
                                } else if (this.point.collapsable == true) {
                                    return this.point.name + '<i class="far fa-minus-square" rel="tooltip" title="Collapse package files" data-id="' + this.point.parentId + '" data-type="' + this.point.sankeyNodeType + '"></i>';
                                } else {
                                    return this.point.name
                                }

                            }
                        }
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


function filterFunction(myinput, mydropdown) {
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

function getSelectedFeatures() {

    var featuresCheckBoxes = document.getElementsByClassName("feature-checkbox");
    var selectedFeatures = [];
    for (var i = 0; i < featuresCheckBoxes.length; i++) {
        if (featuresCheckBoxes[i].checked) {
            selectedFeatures.push(featuresCheckBoxes[i].getAttribute("data-id"));
        }
    }
    return selectedFeatures;
}

function filterSelectedFeatures() {
    var selectedFeatures = getSelectedFeatures();
    if (selectedFeatures.length > 0) {
        $.ajax({
            type: 'POST',
            url: "/filter-features?features=" + selectedFeatures,
            data: JSON.stringify(linksData),
            dataType: "json",
            contentType: "application/json",
            accept: "application/json",
            success: function (data) {
                linksData = data.sankeyLinks;
                nodes = data.nodes;
                sankeyChart.update({
                    plotOptions: {
                        series: {
                            animation: {
                                duration: 2000
                            }
                        }
                    },
                    series: {
                        data: data.sankeyLinks,
                        nodes: data.nodes,

                    },
                    sankey: {
                        nodePadding: 25,
                        dataLabels: {
                            style: {
                                color: 'black',


                            },
                            useHTML: true,
                            enabled: true,
                            nodeFormatter: function () {
                                if (this.point.expandable == true && this.point.collapsable == true) {
                                    return this.point.name + '<i class="far fa-plus-square" rel="tooltip" title="Expand this package" data-id="' + this.point.id + '" data-type="' + this.point.sankeyNodeType + '"></i>' +
                                        '<i class="far fa-minus-square" rel="tooltip" title="Collapse package files" data-id="' + this.point.parentId + '" data-type="' + this.point.sankeyNodeType + '"></i>';
                                } else if (this.point.expandable == true) {
                                    return this.point.name + '<i class="far fa-plus-square" rel="tooltip" title="Expand this package" data-id="' + this.point.id + '" data-type="' + this.point.sankeyNodeType + '"></i>';
                                } else if (this.point.collapsable == true) {
                                    return this.point.name + '<i class="far fa-minus-square" rel="tooltip" title="Collapse package files" data-id="' + this.point.parentId + '" data-type="' + this.point.sankeyNodeType + '"></i>';
                                } else {
                                    return this.point.name
                                }

                            }
                        }
                    }
                });
                expandAndCollapse();
            }

        });

    }


}


function clearFeatureFilters() {
    $.ajax({
        type: 'GET',
        url: "/clear-feature-filters",
        accept: "application/json",
        success: function (data) {
            linksData = data.sankeyLinks;
            nodes = data.nodes;
            sankeyChart.update({
                plotOptions: {
                    series: {
                        animation: {
                            duration: 2000
                        }
                    }
                },
                series: {
                    data: data.sankeyLinks,
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


function filterSelectedPackages() {
    var packagesCheckBoxes = document.getElementsByClassName("package-checkbox");
    var selectedPackages = [];
    for (var i = 0; i < packagesCheckBoxes.length; i++) {
        if (packagesCheckBoxes[i].checked) {
            selectedPackages.push(packagesCheckBoxes[i].getAttribute("data-id"));
        }
    }
    if (selectedPackages.length > 0) {
        var filteredNodes = nodes.filter(node => node.sankeyNodeType != "PACKAGE" || selectedPackages.includes(node.id));
        var filteredLinks = linksData.filter(link => !(["PACKAGEPRODUCT", "FEATUREPACKAGE"].includes(link.sankeyLinkType)) || selectedPackages.includes(link.from) || selectedPackages.includes(link.to));
        updateSankey(filteredLinks, filteredNodes);
    } else {
        updateSankey(linksData, nodes);
    }
}


function filterSelectedProducts() {
    var productsCheckBoxes = document.getElementsByClassName("product-checkbox");
    var selectedProducts = [];
    for (var i = 0; i < productsCheckBoxes.length; i++) {
        if (productsCheckBoxes[i].checked) {
            selectedProducts.push(productsCheckBoxes[i].getAttribute("data-id"));
        }
    }
    if (selectedProducts.length > 0) {
        var filteredNodes = nodes.filter(node => node.sankeyNodeType != "PRODUCT" || selectedProducts.includes(node.id));
        var filteredLinks = linksData.filter(link => !(["PACKAGEPRODUCT", "PRODUCTFEATURE"].includes(link.sankeyLinkType)) || selectedProducts.includes(link.from) || selectedProducts.includes(link.to));
        updateSankey(filteredLinks, filteredNodes);
    } else {
        updateSankey(linksData, nodes);
    }
}

function updateSankey(updatedData, updatedNodes) {
    sankeyChart.update({
        plotOptions: {
            series: {
                animation: {
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


function showLeftPackages(button) {
    $.ajax({
        type: 'POST',
        url: "/left-packages/show",
        data: JSON.stringify({"sankeyLinks": linksData, "nodes": nodes}),
        dataType: "json",
        contentType: "application/json",
        accept: "application/json",
        success: function (data) {
            var activeFilterDiv = document.getElementsByClassName("filter-active")[0];
            activeFilterDiv.classList.add("d-none");
            activeFilterDiv.classList.remove("filter-active");
            var nextFilterDiv;
            if (activeFilterDiv.id == "filter-prod-fea") {
                nextFilterDiv = document.getElementById("filter-pack-prod-fea");
            } else {
                nextFilterDiv = document.getElementById("filter-pack-prod-fea-pack");
            }
            nextFilterDiv.classList.remove("d-none");
            nextFilterDiv.classList.add("filter-active");
            linksData = data.sankeyLinks;
            nodes = data.nodes;
            sankeyChart.update({
                plotOptions: {
                    series: {
                        animation: {
                            duration: 2000
                        }
                    }
                },
                series: {
                    data: data.sankeyLinks,
                    nodes: data.nodes,

                }
            });
            expandAndCollapse();
        }

    });
}


function collapseLeftPackages(button) {
    $.ajax({
        type: 'POST',
        url: "/left-packages/collapse",
        data: JSON.stringify({"sankeyLinks": linksData, "nodes": nodes}),
        dataType: "json",
        contentType: "application/json",
        accept: "application/json",
        success: function (data) {
            var activeFilterDiv = document.getElementsByClassName("filter-active")[0];
            activeFilterDiv.classList.add("d-none");
            activeFilterDiv.classList.remove("filter-active");
            var nextFilterDiv;
            if (activeFilterDiv.id == "filter-pack-prod-fea") {
                nextFilterDiv = document.getElementById("filter-prod-fea");
            } else {
                nextFilterDiv = document.getElementById("filter-prod-fea-pack");
            }
            nextFilterDiv.classList.remove("d-none");
            nextFilterDiv.classList.add("filter-active");
            linksData = data.sankeyLinks;
            nodes = data.nodes;
            sankeyChart.update({
                plotOptions: {
                    series: {
                        animation: {
                            duration: 2000
                        }
                    }
                },
                series: {
                    data: data.sankeyLinks,
                    nodes: data.nodes,

                }
            });
            expandAndCollapse();
        }

    });

}

function showRightPackages(button) {
    $.ajax({
        type: 'POST',
        url: "/right-packages/show",
        data: JSON.stringify({"sankeyLinks": linksData, "nodes": nodes}),
        dataType: "json",
        contentType: "application/json",
        accept: "application/json",
        success: function (data) {
            var activeFilterDiv = document.getElementsByClassName("filter-active")[0];
            activeFilterDiv.classList.add("d-none");
            activeFilterDiv.classList.remove("filter-active");
            var nextFilterDiv;
            if (activeFilterDiv.id == "filter-prod-fea") {
                nextFilterDiv = document.getElementById("filter-prod-fea-pack");
            } else {
                nextFilterDiv = document.getElementById("filter-pack-prod-fea-pack");
            }
            nextFilterDiv.classList.remove("d-none");
            nextFilterDiv.classList.add("filter-active");
            linksData = data.sankeyLinks;
            nodes = data.nodes;
            sankeyChart.update({
                plotOptions: {
                    series: {
                        animation: {
                            duration: 2000
                        }
                    }
                },
                series: {
                    data: data.sankeyLinks,
                    nodes: data.nodes,

                }
            });
            expandAndCollapse();
        }

    });
}


function collapseRightPackages(button) {
    $.ajax({
        type: 'POST',
        url: "/right-packages/collapse",
        data: JSON.stringify({"sankeyLinks": linksData, "nodes": nodes}),
        dataType: "json",
        contentType: "application/json",
        accept: "application/json",
        success: function (data) {
            var activeFilterDiv = document.getElementsByClassName("filter-active")[0];
            activeFilterDiv.classList.add("d-none");
            activeFilterDiv.classList.remove("filter-active");
            var nextFilterDiv;
            if (activeFilterDiv.id == "filter-prod-fea-pack") {
                nextFilterDiv = document.getElementById("filter-prod-fea");
            } else {
                nextFilterDiv = document.getElementById("filter-pack-prod-fea");
            }
            nextFilterDiv.classList.remove("d-none");
            nextFilterDiv.classList.add("filter-active");
            linksData = data.sankeyLinks;
            nodes = data.nodes;
            sankeyChart.update({
                plotOptions: {
                    series: {
                        animation: {
                            duration: 2000
                        }
                    }
                },
                series: {
                    data: data.sankeyLinks,
                    nodes: data.nodes,

                }
            });
            expandAndCollapse();
        }

    });
}


function saveAnalysisHallMark() {
    var hallmarkText = document.getElementById("hallmarkText");
    if (hallmarkText.value == undefined || hallmarkText.value.trim() == "") {
        alert("You must enter an analysis description");
        return;
    }
    let sankeySVG = sankeyChart.getSVG();
    let hallmark = {img: sankeySVG, hallmark: hallmarkText.value};
    let reportHallmarks = window.localStorage.getItem('reportHallmarks');
    if (reportHallmarks == null || reportHallmarks == undefined) {
        reportHallmarks = [];
    } else {
        reportHallmarks = JSON.parse(reportHallmarks);
    }

    reportHallmarks.push(hallmark);
    hallmarkText.value = "";
    $("#hallmarkModal").modal("hide");
    window.localStorage.setItem('reportHallmarks', JSON.stringify(reportHallmarks));


}