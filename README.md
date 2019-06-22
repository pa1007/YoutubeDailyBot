
# YoutubeDailyBot
This is the code of the bot, see the website (Twitter link for more info)

[Twitter Profile](https://twitter.com/DailyMostViewed)


# 1. Files Explication : 
### 1.1 Tweet File :
* [tweet.txt](https://github.com/pa1007/YoutubeDailyBot/blob/master/tweet.txt "tweet.txt")

This File doesn't have any order requiement, do what you whant with the available markup
~~~~ 
{NEXT_LINE} // to go to the next line
{VIDEO_NAME} // The title of the video
{VIDEO_CHANNEL} // Name of the channel
{VIDEO_LINK} // The link to the video
{VIDEO_LIKES_NUMBER} // The number of likes
{VIDEO_VIEWS} //The number of views
~~~~ 
and you can add text between all markups like : 
~~~~ 
{VIDEO_NAME}
from 
{VIDEO_CHANNEL} 
{NEXT_LINE} 
has
{VIDEO_VIEWS} 
views and 
{VIDEO_LIKES_NUMBER} 
likes
{NEXT_LINE}
{VIDEO_LINK}
~~~~
So for this video : 
![Exemple](https://puu.sh/DJisV/55af24345f.png)

~~~~
Tipping Waitresses With Real Gold Bars from Mr.Beast 
Has 7.6M views and 425.81K likes 
https://www.youtube.com/watch?v=Rmf6T_Ewt38
~~~~


### 1.2 Stat File : 

*  [stat.txt](https://github.com/pa1007/YoutubeDailyBot/blob/master/stat.txt "stat.txt")


The file needs to start with the line "FIRST LINE : arg " or the first line will be ignored
you can put the following argument  : 

* AVGVIEWS : Order the video by average views
* AVGLIKES : Order the video by average like
* TOTLIKE : Order the video by total likes
* TOTVIEWS : Order the video by the total view
* NUM or Anything: Order the video by number of time seen

In this file you can put differents markups in it : 
~~~~ 
FIRST LINE : AVGVIEWS //Needed
{NEXT_LINE} // to go to the next line
{VIDEO_NUMBER} // The number of video in the month
{TOTAL_VIEW} // The number total of views
{AVERAGE_VIEW} //The average number of views by video (TOTAL/NUMBER)
{VIDEO_CHANNEL_FIRST} //The name of the channel
{AVERAGE_LIKES} //The number of likes
~~~~
and you can add text between all markups like : 
~~~~ 
FIRST LINE : AVGVIEWS 
{VIDEO_CHANNEL_FIRST}
has uploaded this month champion
{NEXT_LINE} 
Number time saw 
{VIDEO_NUMBER} 
Total view
{TOTAL_VIEW} 
Average Views
{AVERAGE_VIEW}
Average Likes
{AVERAGE_LIKES} 
~~~~
(More to come, added in a need future)

So for this video  **(Assuming this is the only video register for him of the month and is the first of the month )** ;

![Exemple](https://puu.sh/DJisV/55af24345f.png)

~~~~ 
MrBeast has uploaded this month champion
Number time saw 1 Total view 7.6M Average Views 7.6M Average Likes 425.81K
~~~~ 

## APIs Files : 

#### key.txt :
Just one line with your youtube API key

#### twitter4j.properties :
This is a project file so it must be in the ressources folder of the project to work  with the syntax : 
~~~~
debug=true  
oauth.consumerKey=
oauth.consumerSecret=
oauth.accessToken=
oauth.accessTokenSecret=
~~~~
You will need a Twitter dev account to find those ;


