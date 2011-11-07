/*
Core JavaScript functionality for the application.  Preforms the required
Restful calls, validates return values, and populates the member table.
 */

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
      html += '<td><a href="rest/members/' + memId +
               '" target="_blank" class="resturl">XML</a> / <a href="rest/members/' +
               memId + '/json" target="_blank" class="resturl">JSON</a></td>';
   });
   return html;
}

function updateMemberTable() {
   $.get('rest/members',
         function(data) {
            var $members = $(data).find('member');

            $('#members').empty().append(buildMemberRows($members));

         }).error(function(error) {
            var errStatus = error.status;
            console.log("error updating table -" + errStatus);
         });
}

/*
Attempts to register a new member using a JAX-RS POST.  The callbacks
the refresh the member table, or process JAX-RS response codes to update
the validation errors.
 */
function registerMember(formValues) {
   //clear existing error msgs
   $('span.invalid').text('');

   $.post('rest/members', formValues,
         function(data) {
            console.log("Member registered");
            updateMemberTable();
         }).error(function(error) {
            var errStatus = error.status;

            if ((error.status == 409) || (error.status == 400)) {
               console.log("Validation error registering user!");

               var errorMsg = JSON.parse(error.responseText);

               $.each(errorMsg, function(index, val){
                  $('<span class="invalid">' + val + '</span>')
                        .insertAfter($('#' + index));
               });
            } else {
               console.log("error - unknown server issue");
               $('<span class="invalid">Unknown server error</span>')
                        .insertAfter($('#register'));
            }
         });
}