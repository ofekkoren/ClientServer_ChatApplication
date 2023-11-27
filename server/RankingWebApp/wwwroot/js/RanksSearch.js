$(function () {
    $("#searchInRanks").submit(async (e) => {
        e.preventDefault();
        const query = $('#searchQuery').val();
        const result = await fetch('/Ranks/Search/?query=' + query);
        const data = await result.json();
        const template = $('#searchTemplate').html();
        let queryResults = '';
        for (var item in data) {
            let row = template;
            for (var key in data[item]) {
                row = row.replaceAll('{' + key + '}', data[item][key]);
                row = row.replaceAll('%7B' + key + '%7D', data[item][key]);
            }
            queryResults += row;
        }
        $('tbody').html(queryResults);
    });
});