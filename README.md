Graduation project for JavaRush courses
Build a voting system for deciding where to have lunch.

# Application information:
- There're two types of users: admin and regular users.
- Admins can input a restaurant and it's lunch menu of the day.
- It's possible to get a list of restaurants with updated menu.
- Users can vote on which restaurant they want to have lunch at.
- Only one vote counted per user.
- If user votes again the same day:
If it is before 11:00 we asume that he changed his mind.
If it is after 11:00 then it is too late, vote can't be changed
- Each restaurant provides new menu each day.

Project is build on Hibernate, Spring Data, Sping WEB MVC, Spring Security.
