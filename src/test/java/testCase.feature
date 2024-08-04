Feature: To test Fitpeo homepage Slider function

  Background:
    Given user has started a browser


    Scenario: To verify the Slider function of the user
      Given user navigates to Fitpeo Home page
      Then user should click on Revenue Calculator tab
      When user scrolls to the Slider
      And user moves the slider to "820" value
      Then user should validate the slider position when value entered is "560"
      Then user should validate the slider position when value entered is "820"
      And user selects below checkboxes
      |  CPT-99091 |
      |  CPT-99453 |
      |  CPT-99454 |
      |  CPT-99474 |
      Then user should validate that the total recurring reimbursement value is "$110700"
