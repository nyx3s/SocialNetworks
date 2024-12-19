# Social Network Analysis 
_My capstone project for the [Object Oriented Java Programming: Data Structures and Beyond Specialization](https://www.coursera.org/specializations/java-object-oriented), offered by University of California San Diego ( UCSD )._

This project investigates the communities within a social network. The first part of the project will look at relationships between a user’s friends (specifically, are they all connected as friends too). In the second part, the goal will be to find the minimum dominating set, so we can reach all the people in the network just sharing with a number of friends.

#### Small Test Case

### Data
facebook_ucsd.txt
This graph contains Facebook friendships between students at UCSD in 2005. 
This data was originally stored in a Matlab sparse matrix; however, this was 
processed using Python to create a more suitable format for reading with Java.
The edges in this file are directed; however, each edge also appears in reverse
order, making the final result undirected.

Source: https://archive.org/details/oxford-2005-facebook-matrix

Citation: Facebook data scrape related to paper "The Social Structure of Facebook 
Networks", by Amanda L. Traud, Peter J. Mucha, Mason A. Porter.

- Number of nodes (users) = 14947

### Questions
**Easier:** For a given user, which of their friends aren’t connected as friends? For example, if the given user, Maria, is friends
with both Jamaal and Huang, if Jamaal and Huang are not friends, we’ll suggest them as potential friends.

**Harder:** Find the Exact Cover Set. Figure out the smallest set of people who are connected to everyone in the network. For example, if everyone in the smallest set were to post something to their friends, everyone would see the post.

### Algorithms, Data Structures, and Answer to Question
