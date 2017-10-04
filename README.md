# ad-campaign-demo

A) Compiling and Running

	Run the following command in project root.

		 mvn package && java -jar target/advert_campaign-0.0.1-SNAPSHOT.jar

	The application runs on port 8080.

B) Tables
		
		A table containing the set of users
		A table contaning campaigns for each user.


C) Operations

	1) A user is created before a campaign is created. An http POST to the following url creates the user.
		
		http://localhost:8080/user/addUser	

			{
				"firstName":"ab",
				"lastName":"xy",
				"middleName":"mn",
				"userId":"user1"	

			}

	2) All users are retrieved using the following GET operation.

			http://localhost:8080/user/all


	3) A single user's details are available through the following GET operation.

			http://localhost:8080/user/user1


	4) A campaign for user is created using POST and with the data in Json format. Any number of campaigns can be posted.
   

			http://localhost:8080/ad	

			{
				"partner_id":"user1",
				"duration":"120",
				"ad_content":"ad content1"
			}

 
	5) The current active campaign for a user is the most recent campaign that is posted and where the current Date/Time falls within the campaign's create Date/Time and end Date/Time. This can be retrived using the following GET operation.

			http://localhost:8080/ad/user1


	6) All campaigns for all users can be seen through the following GET operation.

			http://localhost:8080/ad/all

	7) All campaigns for a user is can be retrieved through the following GET operation.

			http://localhost:8080/ad/all/user1

	8) A current campaign is any campaign where the current Date/Time falls within the campaign's create Date/Time and end Date/Time. All current campaigns can be retrieved using the following GET operation.

			http://localhost:8080/ad/current


	9) Current camapigns for a user is available via GET.

			http://localhost:8080/ad/current/user2

D) Test Cases

		Intial data set up is the storing for some users and associated campaign data into the tables.

		TEST CASE 1

			User A details are retrieved using GET and validated.

		TEST CASE 2
		
			Validates that the active campaign for a user is the most recent campaign having the Current Date and Time between the campaign's create Date/Time and end Date/Time. 	

		TEST CASE 3

			Validates that no active campaign exists for a user after some campaigns are stored and a certain time greater than the durations of the campaigns have elapsed.



E) Persistence details

	2 layers between rest end point and database. 	
			a) JPA layer
			b) Persistence framework(hibernate)


F) Advantages and disadvantages of the Persistence mechanism.

		
		Advantages:

			a) Can conveniently change the persistence layer and database layer.
			b) Operations are performed through Java APIs

		Disadvantages
		
			a) Don't have much visbility into database operations.
			b) May not be able use certain database features since they may not be available through JPA
			c) More layers means there is some degrading of performance. 




For deployment purposed the application would be packaged as a WAR and run in a Servlet container.





















