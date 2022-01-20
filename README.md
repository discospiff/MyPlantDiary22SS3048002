# My Plant Diary
Design Document

## Group Members:
- Brandan Jones

## Introduction

Do you remember where you planted your apple tree?  Do you know when to water and fertilize your plants?  MyPlantDiary can help you:

- Record dates and locations where you planted plants.
- Tkae an view photos of a plant throughout its life
- Record when you added water, fertilizer, and other ammendments.
- Be aware of upcoming events for a plant.

Use your Android device to create your own plant diary.  Tkae photos with the on-device camera.  Create reminders based on what you did in previous years.  Receive alerts for your plants.

## Storyboard

![MPD Storyboard](https://user-images.githubusercontent.com/2224876/150370363-80ac82a8-383b-4d03-9db3-ab8073f14f41.png)

## Functional Requirements

### Requirement 100.0: Search for Plants

#### SCENARIO
As a user interested in plants, I want to be able to search plants based on any part of the name: genus, species, cultivar, or common name so that I can select a plant to associate with a specimen.
#### DEPENDENCIES
Plant search data are available and accessible.
#### ASSUMPTIONS
Scientific names are stated in Latin.
Common names are stated in English.
#### EXAMPLES
1.1
**Given** a feed of plant data is available
**When** I search for “Redbud”
**Then** I should receive at least one result with these attributes: 
Genus: Cercis
Species: canadensis
Common: Eastern Redbud 
1.2
**Given** a feed of plant data is available
**When** I search for “Quercus”
**Then** I should receive at least one result with these attributes: 
Genus: Quercus
Species: robur
Common: English Oak
And I should receive at least one result with these attributes:
Genus: Quercus
Species: alba
Common: White Oak

1.3
Given a feed of plant data is available
When I search for “sklujapouetllkjsda;u”
Then I should receive zero results (an empty list)


## Class Diagram
![My Plant Diary Class Diagram](https://raw.githubusercontent.com/discospiff/MyPlantDiary22SS3048002/UML/MyPlantDiaryClassDiagram.drawio.png)

## Scrum Roles
- Product Owner/Scrum Master: Brandan Jones
- UI Developer: Brandan Jones
- Integration Specialist: Brandan Jones

## Standup
Sundays at 8:00 on Teams
