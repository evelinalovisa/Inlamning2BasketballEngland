Feature: Användarregistrering

  Scenario: Lyckad användarregistrering
    Given Jag är på registreringssidan
    When Jag fyller i alla obligatoriska fält korrekt
    When Jag godkänner alla villkor
    When Jag klickar på registreringsknappen
    Then Mitt konto bör skapas framgångsrikt

    Scenario: Skapa användare där efternamn saknas
      Given Jag är på registreringssidan
      When Jag fyller i alla obligatoriska fält förutom efternamn
      When Jag godkänner alla villkor
      When Jag klickar på registreringsknappen
      Then Registreringen ska misslyckas med ett felmeddelande om att efternamn saknas

    Scenario: Skapa användare där lösenorden inte matchar
      Given Jag är på registreringssidan
      When Jag fyller i alla obligatoriska fält med olika lösenord
      When Jag godkänner alla villkor
      When Jag klickar på registreringsknappen
      Then registreringen ska misslyckas med ett felmeddelande om att lösenorden inte matchar

    Scenario: Skapa användare där terms and conditions inte är godkända
      Given Jag är på registreringssidan
      When Jag fyller i alla obligatoriska fält korrekt
      When Jag godkänner inte terms and conditions
      When Jag klickar på registreringsknappen
      Then Registreringen ska misslyckas med ett felmeddelande om att terms and conditions måste godkännas