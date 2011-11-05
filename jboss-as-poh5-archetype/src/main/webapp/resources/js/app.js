function buildMemberRows(members) {
   var html = '';
   $(members).each(function() {
      var $member = $(this);
      html += '<tr class="member">';
      var memId = $member.find('id').text();
      html += '<td>' + memId + '</td>';
      html += '<td>' + $member.find('name').text() + '</td>';
      html += '<td>' + $member.find('email').text() + '</td>';
      html += '<td>' + $member.find('phoneNumber').text() + '</td>';
      html += '<td><a href="rest/members/' + memId + '" target="_blank" class="resturl">XML</a> / JSON</td>';
   });
   return html;
}

function updateMemberTable() {
   $.get('rest/members',
         function(data) {
            var $members = $(data).find('member');

            console.log("Member count: " + $members.length);

            $('#members').empty()
                  .append(buildMemberRows($members));

         }).error(function(error) {
            var errStatus = error.status;
            console.log("error updating table -" + errStatus);
         });
}

