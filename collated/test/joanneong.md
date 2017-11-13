# joanneong
###### /java/guitests/guihandles/CommandBoxHandle.java
``` java
    /**
     * Enters the given input in the {@code CommandBox} without executing the input and
     * without using autocompletion.
     */
    public void enterInputWithoutAutocompletion(String input) {
        click();
        guiRobot.interact(() -> getRootNode().setText(input));
    }

    /**
     * Enters the given input in the {@code CommandBox} without executing the input and
     * chooses the first option in the auto-complete suggestions.
     *
     * Note that the input is not executed.
     */
    public void enterInput(String input) {
        enterInputWithoutAutocompletion(input);

        guiRobot.type(KeyCode.TAB);
    }

```
###### /java/guitests/guihandles/GraphDisplayHandle.java
``` java
/**
 * A handler for the {@code GraphDisplay} of the UI.
 */
public class GraphDisplayHandle extends NodeHandle<Node> {

    public static final String DISPLAY_ID = "#graphDisplay";
    private SwingNode graphDisplayNode;

    public GraphDisplayHandle(SwingNode graphDisplayNode) {
        super(graphDisplayNode);

        this.graphDisplayNode = graphDisplayNode;
    }

    /**
     * Returns the JComponent (i.e. the viewer) embedded in the SwingNode.
     */
    public JComponent getContent() {
        return graphDisplayNode.getContent();
    }
}
```
###### /java/seedu/address/logic/commands/AddCommandTest.java
``` java
        @Override
        public void editRelationship(Index indexFromPerson, Index indexToPerson, Name name,
                              ConfidenceEstimate confidenceEstimate)
                throws IllegalValueException, RelationshipNotFoundException, DuplicateRelationshipException {
            fail("This method should not be called.");
        };

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        public void removeTag(String tagToBeRemoved) throws TagNotFoundException, IllegalValueException {
            fail("This method should not be called.");
        }
```
###### /java/seedu/address/logic/commands/EditRelationshipCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for EditRelationshipCommand.
 */
public class EditRelationshipCommandTest {

    private Model model = new ModelManager(getTypicalAddressBookWithRelationships(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() throws Exception {
        try {
            int lengthOfPersonList = model.getFilteredPersonList().size();
            Index firstPerson = Index.fromOneBased(lengthOfPersonList - 1);
            Index secondPerson = Index.fromOneBased(lengthOfPersonList);

            Name newName = new Name("friends");
            ConfidenceEstimate newConfidenceEstimate = new ConfidenceEstimate(10);

            EditRelationshipCommand editRelationshipCommand = prepareCommand(firstPerson, secondPerson,
                    newName, newConfidenceEstimate);

            String expectedMessage = String.format(EditRelationshipCommand.MESSAGE_EDIT_RELATIONSHIP_SUCCESS,
                    firstPerson, secondPerson);

            ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.editRelationship(firstPerson, secondPerson, newName, newConfidenceEstimate);

            assertCommandSuccess(editRelationshipCommand, model, expectedMessage, expectedModel);
        } catch (IllegalValueException ive) {
            fail("This is not supposed to fail");
        }
    }

    @Test
    public void execute_nameFieldSpecified_success() throws Exception {
        try {
            int lengthOfPersonList = model.getFilteredPersonList().size();
            Index firstPerson = Index.fromOneBased(lengthOfPersonList - 1);
            Index secondPerson = Index.fromOneBased(lengthOfPersonList);

            Name newName = new Name("friends");
            ReadOnlyPerson fromPerson = model.getFilteredPersonList().get(firstPerson.getZeroBased());
            Relationship relationship = fromPerson.getRelationships().iterator().next();
            ConfidenceEstimate originalConfidenceEstimate = relationship.getConfidenceEstimate();

            EditRelationshipCommand editRelationshipCommand = prepareCommand(firstPerson, secondPerson,
                    newName, originalConfidenceEstimate);

            String expectedMessage = String.format(EditRelationshipCommand.MESSAGE_EDIT_RELATIONSHIP_SUCCESS,
                    firstPerson, secondPerson);

            ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.editRelationship(firstPerson, secondPerson, newName, originalConfidenceEstimate);

            assertCommandSuccess(editRelationshipCommand, model, expectedMessage, expectedModel);
        } catch (IllegalValueException ive) {
            fail("This is not supposed to fail");
        }
    }

    @Test
    public void execute_confidenceEstimateFieldSpecified_success() throws Exception {
        try {
            int lengthOfPersonList = model.getFilteredPersonList().size();
            Index firstPerson = Index.fromOneBased(lengthOfPersonList - 1);
            Index secondPerson = Index.fromOneBased(lengthOfPersonList);

            ConfidenceEstimate newConfidenceEstimate = new ConfidenceEstimate(90);
            ReadOnlyPerson fromPerson = model.getFilteredPersonList().get(firstPerson.getZeroBased());
            Relationship relationship = fromPerson.getRelationships().iterator().next();
            Name name = relationship.getName();

            EditRelationshipCommand editRelationshipCommand = prepareCommand(firstPerson, secondPerson,
                   name, newConfidenceEstimate);

            String expectedMessage = String.format(EditRelationshipCommand.MESSAGE_EDIT_RELATIONSHIP_SUCCESS,
                    firstPerson, secondPerson);

            ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.editRelationship(firstPerson, secondPerson, name, newConfidenceEstimate);

            assertCommandSuccess(editRelationshipCommand, model, expectedMessage, expectedModel);
        } catch (IllegalValueException ive) {
            fail("This is not supposed to fail");
        }
    }

    @Test
    public void execute_noFieldsSpecified_success() throws Exception {
        try {
            int lengthOfPersonList = model.getFilteredPersonList().size();
            Index firstPerson = Index.fromOneBased(lengthOfPersonList - 1);
            Index secondPerson = Index.fromOneBased(lengthOfPersonList);

            ReadOnlyPerson fromPerson = model.getFilteredPersonList().get(firstPerson.getZeroBased());
            Relationship relationship = fromPerson.getRelationships().iterator().next();
            Name name = relationship.getName();
            ConfidenceEstimate cE = relationship.getConfidenceEstimate();

            EditRelationshipCommand editRelationshipCommand = prepareCommand(firstPerson, secondPerson,
                    name, cE);

            String expectedMessage = String.format(EditRelationshipCommand.MESSAGE_EDIT_RELATIONSHIP_SUCCESS,
                    firstPerson, secondPerson);

            ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.editRelationship(firstPerson, secondPerson, name, cE);

            assertCommandSuccess(editRelationshipCommand, model, expectedMessage, expectedModel);
        } catch (IllegalValueException ive) {
            fail("This is not supposed to fail");
        }
    }

    @Test
    public void execute_invalidFromPersonIndex_throwsCommandException() throws Exception {
        try {
            int lengthOfPersonList = model.getFilteredPersonList().size();
            Index firstPerson = Index.fromOneBased(lengthOfPersonList - 1);
            Index outOfBoundIndex = Index.fromOneBased(lengthOfPersonList + 1);
            Name newName = new Name("friends");
            ConfidenceEstimate newConfidenceEstimate = new ConfidenceEstimate(90);

            EditRelationshipCommand editRelationshipCommand = prepareCommand(firstPerson, outOfBoundIndex,
                    newName, newConfidenceEstimate);

            assertCommandFailure(editRelationshipCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        } catch (IllegalValueException ive) {
            fail("This is not supposed to fail");
        }
    }

    @Test
    public void execute_invalidToPersonIndex_throwsCommandException() throws Exception {
        try {
            int lengthOfPersonList = model.getFilteredPersonList().size();
            Index outOfBoundIndex = Index.fromOneBased(lengthOfPersonList + 1);
            Index secondPerson = Index.fromOneBased(lengthOfPersonList - 1);
            Name newName = new Name("friends");
            ConfidenceEstimate newConfidenceEstimate = new ConfidenceEstimate(90);

            EditRelationshipCommand editRelationshipCommand = prepareCommand(outOfBoundIndex, secondPerson,
                    newName, newConfidenceEstimate);

            assertCommandFailure(editRelationshipCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        } catch (IllegalValueException ive) {
            fail("This is not supposed to fail");
        }
    }

    @Test
    public void execute_invalidRelationship_throwsCommandException() throws Exception {
        try {
            Name name = new Name("friends");
            ConfidenceEstimate confidenceEstimate = new ConfidenceEstimate(90);

            EditRelationshipCommand editRelationshipCommand = prepareCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON,
                    name, confidenceEstimate);

            assertCommandFailure(editRelationshipCommand, model, Messages.MESSAGE_RELATIONSHIP_NOT_FOUND);
        } catch (IllegalValueException ive) {
            fail("This is not supposed to fail");
        }
    }

    @Test
    public void equals() {
        EditRelationshipCommand editRelationshipFirstCommand =
                new EditRelationshipCommand(TypicalIndexes.INDEX_FIRST_PERSON, TypicalIndexes.INDEX_SECOND_PERSON,
                        Name.UNSPECIFIED, ConfidenceEstimate.UNSPECIFIED);
        EditRelationshipCommand editRelationshipSecondCommand =
                new EditRelationshipCommand(TypicalIndexes.INDEX_SECOND_PERSON, TypicalIndexes.INDEX_THIRD_PERSON,
                        Name.UNSPECIFIED, ConfidenceEstimate.UNSPECIFIED);

        // same object -> returns true
        assertTrue(editRelationshipFirstCommand.equals(editRelationshipFirstCommand));

        // same values -> returns true
        EditRelationshipCommand editRelationshipFirstCommandCopy =
                new EditRelationshipCommand(TypicalIndexes.INDEX_FIRST_PERSON, TypicalIndexes.INDEX_SECOND_PERSON,
                Name.UNSPECIFIED, ConfidenceEstimate.UNSPECIFIED);
        assertTrue(editRelationshipFirstCommand.equals(editRelationshipFirstCommandCopy));

        // different types -> returns false
        assertFalse(editRelationshipFirstCommand.equals(1));

        // null -> returns false
        assertFalse(editRelationshipFirstCommand.equals(null));

        // different from person and to person -> returns false
        assertFalse(editRelationshipFirstCommand.equals(editRelationshipSecondCommand));
    }

    /**
     * Returns an {@code EditRelationshipCommand} with the parameters fromPerson and toPerson indexes.
     */
    private EditRelationshipCommand prepareCommand(Index fromPerson, Index toPerson, Name name, ConfidenceEstimate ce) {
        EditRelationshipCommand editRelationshipCommand = new EditRelationshipCommand(fromPerson, toPerson, name, ce);
        editRelationshipCommand.setData(model, new CommandHistory(), new UndoRedoStack(), new StorageStub());
        return editRelationshipCommand;
    }
}
```
###### /java/seedu/address/logic/commands/FindCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        AnyContainsKeywordsPredicate firstPredicate =
                new AnyContainsKeywordsPredicate(Collections.singletonList("first"));
        AnyContainsKeywordsPredicate secondPredicate =
                new AnyContainsKeywordsPredicate(Collections.singletonList("second"));
        NameContainsKeywordsPredicate thirdPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("third"));
        NameContainsKeywordsPredicate fourthPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("fourth"));
        AddressContainsKeywordsPredicate fifthPredicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("fifth"));
        AddressContainsKeywordsPredicate sixthPredicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("sixth"));
        EmailContainsKeywordsPredicate seventhPredicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("seventh"));
        EmailContainsKeywordsPredicate eighthPredicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("eighth"));
        PhoneContainsKeywordsPredicate ninthPredicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("ninth"));
        PhoneContainsKeywordsPredicate tenthPredicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("tenth"));
        TagContainsKeywordsPredicate eleventhPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("eleventh"));
        TagContainsKeywordsPredicate twelfthPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("twelfth"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);
        FindCommand findThirdCommand = new FindCommand(thirdPredicate);
        FindCommand findFourthCommand = new FindCommand(fourthPredicate);
        FindCommand findFifthCommand = new FindCommand(fifthPredicate);
        FindCommand findSixthCommand = new FindCommand(sixthPredicate);
        FindCommand findSeventhCommand = new FindCommand(seventhPredicate);
        FindCommand findEighthCommand = new FindCommand(eighthPredicate);
        FindCommand findNinthCommand = new FindCommand(ninthPredicate);
        FindCommand findTenthCommand = new FindCommand(tenthPredicate);
        FindCommand findEleventhCommand = new FindCommand(eleventhPredicate);
        FindCommand findTwelfthCommand = new FindCommand(twelfthPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));
        assertTrue(findThirdCommand.equals(findThirdCommand));
        assertTrue(findFifthCommand.equals(findFifthCommand));
        assertTrue(findSeventhCommand.equals(findSeventhCommand));
        assertTrue(findNinthCommand.equals(findNinthCommand));
        assertTrue(findEleventhCommand.equals(findEleventhCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));
        FindCommand findThirdCommandCopy = new FindCommand(thirdPredicate);
        assertTrue(findThirdCommand.equals(findThirdCommandCopy));
        FindCommand findFifthCommandCopy = new FindCommand(fifthPredicate);
        assertTrue(findFifthCommand.equals(findFifthCommandCopy));
        FindCommand findSeventhCommandCopy = new FindCommand(seventhPredicate);
        assertTrue(findSeventhCommand.equals(findSeventhCommandCopy));
        FindCommand findNinthCommandCopy = new FindCommand(ninthPredicate);
        assertTrue(findNinthCommand.equals(findNinthCommandCopy));
        FindCommand findEleventhCommandCopy = new FindCommand(eleventhPredicate);
        assertTrue(findEleventhCommand.equals(findEleventhCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));
        assertFalse(findThirdCommand.equals(1));
        assertFalse(findFifthCommand.equals(1));
        assertFalse(findSeventhCommand.equals(1));
        assertFalse(findNinthCommand.equals(1));
        assertFalse(findEleventhCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));
        assertFalse(findThirdCommand.equals(null));
        assertFalse(findFifthCommand.equals(null));
        assertFalse(findSeventhCommand.equals(null));
        assertFalse(findNinthCommand.equals(null));
        assertFalse(findEleventhCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
        assertFalse(findThirdCommand.equals(findFourthCommand));
        assertFalse(findFifthCommand.equals(findSixthCommand));
        assertFalse(findSeventhCommand.equals(findEighthCommand));
        assertFalse(findNinthCommand.equals(findTenthCommand));
        assertFalse(findEleventhCommand.equals(findTwelfthCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = expectedMessage(0);

        FindCommand command = prepareGlobalCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());

        FindCommand nameCommand = prepareNameCommand(" ");
        assertCommandSuccess(nameCommand, expectedMessage, Collections.emptyList());

        FindCommand addressCommand = prepareAddressCommand(" ");
        assertCommandSuccess(addressCommand, expectedMessage, Collections.emptyList());

        FindCommand emailCommand = prepareEmailCommand(" ");
        assertCommandSuccess(emailCommand, expectedMessage, Collections.emptyList());

        FindCommand phoneCommand = preparePhoneCommand(" ");
        assertCommandSuccess(phoneCommand, expectedMessage, Collections.emptyList());

        FindCommand tagCommand = prepareTagCommand(" ");
        assertCommandSuccess(tagCommand, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_singleKeyword_onePersonFound() {
        String expectedMessage = expectedMessage(1);

        FindCommand command = prepareGlobalCommand("Kurz");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL));

        FindCommand nameCommand = prepareNameCommand("Elle");
        assertCommandSuccess(nameCommand, expectedMessage, Arrays.asList(ELLE));

        FindCommand addressCommand = prepareAddressCommand("10th");
        assertCommandSuccess(addressCommand, expectedMessage, Arrays.asList(DANIEL));

        FindCommand emailCommand = prepareEmailCommand("alice@example.com");
        assertCommandSuccess(emailCommand, expectedMessage, Arrays.asList(ALICE));

        FindCommand phoneCommand = preparePhoneCommand("948242");
        assertCommandSuccess(phoneCommand, expectedMessage, Arrays.asList(FIONA));

        FindCommand tagCommand = prepareTagCommand("owesMoney");
        assertCommandSuccess(tagCommand, expectedMessage(2), Arrays.asList(BENSON, FIONA));
    }

    @Test
    public void execute_singleKeyword_multiplePersonsFound() {
        FindCommand command = prepareGlobalCommand("Meier");
        assertCommandSuccess(command, expectedMessage(2), Arrays.asList(BENSON, DANIEL));

        FindCommand nameCommand = prepareNameCommand("Meier");
        assertCommandSuccess(nameCommand, expectedMessage(2), Arrays.asList(BENSON, DANIEL));

        FindCommand addressCommand = prepareAddressCommand("ave");
        assertCommandSuccess(addressCommand, expectedMessage(3), Arrays.asList(ALICE, BENSON, ELLE));

        FindCommand emailCommand = prepareEmailCommand("@example.com");
        assertCommandSuccess(emailCommand, expectedMessage(7),
                Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));

        FindCommand phoneCommand = preparePhoneCommand("94824");
        assertCommandSuccess(phoneCommand, expectedMessage(2), Arrays.asList(FIONA, GEORGE));

        FindCommand tagCommand = prepareTagCommand("friends");
        assertCommandSuccess(tagCommand, expectedMessage(5),
                Arrays.asList(ALICE, BENSON, CARL, DANIEL, GEORGE));
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        FindCommand command = prepareGlobalCommand("Kurz Elle Kunz");
        assertCommandSuccess(command, expectedMessage(3), Arrays.asList(CARL, ELLE, FIONA));

        FindCommand nameCommand = prepareNameCommand("Kurz Elle Kunz");
        assertCommandSuccess(nameCommand, expectedMessage(3), Arrays.asList(CARL, ELLE, FIONA));

        FindCommand addressCommand = prepareAddressCommand("ave 10");
        assertCommandSuccess(addressCommand, expectedMessage(4), Arrays.asList(ALICE, BENSON, DANIEL, ELLE));

        FindCommand emailCommand = prepareEmailCommand("alice anna");
        assertCommandSuccess(emailCommand, expectedMessage(2), Arrays.asList(ALICE, GEORGE));

        FindCommand phoneCommand = preparePhoneCommand("953  94824");
        assertCommandSuccess(phoneCommand, expectedMessage(3), Arrays.asList(CARL, FIONA, GEORGE));

        FindCommand tagCommand = prepareTagCommand("owesMoney enemy");
        assertCommandSuccess(tagCommand, expectedMessage(3), Arrays.asList(BENSON, CARL, FIONA));
    }

    /**
     * Formats the expected message and changes the message according to the number of persons found.
     * @param personsFound
     */
    private String expectedMessage(int personsFound) {
        return String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, personsFound);
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand} for a global find.
     */
    private FindCommand prepareGlobalCommand(String userInput) {
        FindCommand command =
                new FindCommand(new AnyContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new StorageStub());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand} for a name search.
     */
    private FindCommand prepareNameCommand(String userInput) {
        FindCommand command =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new StorageStub());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand} for an address search.
     */
    private FindCommand prepareAddressCommand(String userInput) {
        FindCommand command =
                new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new StorageStub());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand} for an email search.
     */
    private FindCommand prepareEmailCommand(String userInput) {
        FindCommand command =
                new FindCommand(new EmailContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new StorageStub());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand} for a phone search.
     */
    private FindCommand preparePhoneCommand(String userInput) {
        FindCommand command =
                new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new StorageStub());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand} for a tag search.
     */
    private FindCommand prepareTagCommand(String userInput) {
        FindCommand command =
                new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new StorageStub());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindCommand command, String expectedMessage, List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new AnyContainsKeywordsPredicate(keywords)), command);

        FindCommand nameCommand = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " n/" + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), nameCommand);

        FindCommand addressCommand = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " a/" + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new AddressContainsKeywordsPredicate(keywords)), addressCommand);

        FindCommand emailCommand = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " e/" + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new EmailContainsKeywordsPredicate(keywords)), emailCommand);

        FindCommand phoneCommand = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " p/" + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new PhoneContainsKeywordsPredicate(keywords)), phoneCommand);

        FindCommand tagCommand = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " t/" + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new TagContainsKeywordsPredicate(keywords)), tagCommand);
    }

```
###### /java/seedu/address/logic/parser/EditRelationshipCommandParserTest.java
``` java
public class EditRelationshipCommandParserTest {

    private EditRelationshipCommandParser parser = new EditRelationshipCommandParser();

    @Test
    public void parse_validArgs_returnsEditRelationshipCommand() {
        assertParseSuccess(parser, "1 2", new EditRelationshipCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON,
                Name.UNSPECIFIED, ConfidenceEstimate.UNSPECIFIED));
    }

    @Test
    public void parse_validArgsWithNameAndCE_returnsEditRelationshipCommand() throws Exception {
        try {
            Name name = new Name("friends");
            ConfidenceEstimate confidenceEstimate = new ConfidenceEstimate(90);
            assertParseSuccess(parser, "1 2 n/friends ce/90",
                    new EditRelationshipCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, name, confidenceEstimate));
        } catch (IllegalValueException ive) {
            fail("This is not supposed to fail");
        }
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditRelationshipCommand.MESSAGE_USAGE));
    }

}
```
###### /java/seedu/address/logic/parser/FindCommandParserTest.java
``` java
public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedGlobalFindCommand =
                new FindCommand(new AnyContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedGlobalFindCommand);

        FindCommand expectedNameFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, " n/Alice Bob", expectedNameFindCommand);

        FindCommand expectedAddressFindCommand =
                new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList("Serangoon", "Bishan")));
        assertParseSuccess(parser, " a/Serangoon Bishan", expectedAddressFindCommand);

        FindCommand expectedEmailFindCommand =
                new FindCommand(new EmailContainsKeywordsPredicate(
                        Arrays.asList("alice@example.com", "Bob@gmail.com")));
        assertParseSuccess(parser, " e/alice@example.com Bob@gmail.com", expectedEmailFindCommand);

        FindCommand expectedPhoneFindCommand =
                new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("12345678", "98454632")));
        assertParseSuccess(parser, " p/12345678 98454632", expectedPhoneFindCommand);

        FindCommand expectedTagFindCommand =
                new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList("friends", "enemy")));
        assertParseSuccess(parser, " t/friends enemy", expectedTagFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedGlobalFindCommand);
        assertParseSuccess(parser, " n/\n Alice \n \t Bob  \t", expectedNameFindCommand);
        assertParseSuccess(parser, " a/\n Serangoon \n \t Bishan  \t", expectedAddressFindCommand);
        assertParseSuccess(parser, " e/\n alice@example.com \n \t Bob@gmail.com  \t", expectedEmailFindCommand);
        assertParseSuccess(parser, " p/\n 12345678 \n \t 98454632  \t", expectedPhoneFindCommand);
        assertParseSuccess(parser, " t/\n friends \n \t enemy  \t", expectedTagFindCommand);


    }

}
```
###### /java/seedu/address/model/TagContainsKeywordsPredicateTest.java
``` java
public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagContainsKeywordsPredicate firstPredicate = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        TagContainsKeywordsPredicate secondPredicate = new TagContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.singletonList("friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Multiple keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("friends", "family"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "family").build()));

        // Only one matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("friends", "enemy"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "family").build()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("FRiEndS", "faMIlY"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "family").build()));

        // Partial keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("frie", "fami"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "family").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("enemy"));
        assertFalse(predicate.test(new PersonBuilder().withTags("friends", "family").build()));

        // Keywords match phone, email, address and name, but does not match tag
        predicate = new TagContainsKeywordsPredicate(
                Arrays.asList("12345", "alice@email.com", "Alice", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withTags("friends").build()));
    }
}

```
###### /java/seedu/address/relationship/RelationshipTest.java
``` java
public class RelationshipTest {

    @Test
    public void equals() {
        Relationship relationshipOne = new RelationshipBuilder().getRelationship();
        ReadOnlyPerson fromPerson = relationshipOne.getFromPerson();
        ReadOnlyPerson toPerson = relationshipOne.getToPerson();
        RelationshipDirection relationshipDirection = relationshipOne.getDirection();
        Name name = relationshipOne.getName();
        ConfidenceEstimate confidenceEstimate = relationshipOne.getConfidenceEstimate();

        ReadOnlyPerson differentFromPerson = new PersonBuilder().withName("Alice").build();
        ReadOnlyPerson differentToPerson = new PersonBuilder().withName("Bob").build();
        RelationshipDirection differentRelationshipDirection = RelationshipDirection.DIRECTED;

        // same object -> returns true
        assertTrue(relationshipOne.equals(relationshipOne));

        // same from person, to person, and direction -> returns true
        Relationship relationshipTwo = new RelationshipBuilder(fromPerson, toPerson,
                relationshipDirection).getRelationship();
        assertTrue(relationshipOne.equals(relationshipTwo));

        // same from person, to person, direction, name, and confidence estimate -> returns true
        Relationship relationshipThree = new RelationshipBuilder(fromPerson, toPerson, relationshipDirection,
                name, confidenceEstimate).getRelationship();
        assertTrue(relationshipOne.equals(relationshipThree));

        // null -> returns false
        assertFalse(relationshipOne.equals(null));

        // different types -> returns false
        assertFalse(relationshipOne.equals("123"));

        // different from person -> returns false
        Relationship relationshipFour = new RelationshipBuilder(differentFromPerson, toPerson, relationshipDirection,
                name, confidenceEstimate).getRelationship();
        assertFalse(relationshipOne.equals(relationshipFour));

        // different to person -> returns false
        Relationship relationshipFive = new RelationshipBuilder(fromPerson, differentToPerson, relationshipDirection,
                name, confidenceEstimate).getRelationship();
        assertFalse(relationshipOne.equals(relationshipFive));

        // different direction -> returns false
        Relationship relationshipSix = new RelationshipBuilder(fromPerson, toPerson, differentRelationshipDirection,
                name, confidenceEstimate).getRelationship();
        assertFalse(relationshipOne.equals(relationshipSix));
    }

}
```
###### /java/seedu/address/testutil/RelationshipBuilder.java
``` java
/**
 * A utility class to help with building Relationships between two persons.
 */
public class RelationshipBuilder {

    private static final Person DEFAULT_FROM_PERSON = new PersonBuilder().build();
    private static final Person DEFAULT_TO_PERSON = new PersonBuilder().withName("Intelli").build();
    private static final RelationshipDirection DEFAULT_DIRECTION = RelationshipDirection.UNDIRECTED;

    private Relationship relationship;

    public RelationshipBuilder() {
        this.relationship = new Relationship(DEFAULT_FROM_PERSON, DEFAULT_TO_PERSON, DEFAULT_DIRECTION);
    }

    public RelationshipBuilder(ReadOnlyPerson fromPerson, ReadOnlyPerson toPerson, RelationshipDirection direction) {
        this.relationship = new Relationship(fromPerson, toPerson, direction);
    }

    public RelationshipBuilder(ReadOnlyPerson fromPerson, ReadOnlyPerson toPerson, RelationshipDirection direction,
                               Name name, ConfidenceEstimate confidenceEstimate) {
        this.relationship = new Relationship(fromPerson, toPerson, direction, name, confidenceEstimate);
    }

    public Relationship getRelationship() {
        return relationship;
    }
}
```
###### /java/seedu/address/ui/CommandBoxTest.java
``` java
    @Test
    public void handleKeyPress_startingWithControl() {
        // empty input
        guiRobot.push(KeyCode.CONTROL);
        assertEmptyCommandBox();

        // single word input
        commandBoxHandle.enterInput(COMMAND_THAT_SUCCEEDS);
        guiRobot.push(KeyCode.CONTROL);
        assertEmptyCommandBox();

        // multi-words input
        commandBoxHandle.enterInput(COMMAND_THAT_FAILS);
        guiRobot.push(KeyCode.CONTROL);
        assertEmptyCommandBox();
    }

```
###### /java/seedu/address/ui/CommandBoxTest.java
``` java
    /**
     * Checks that the input in the {@code commandBox} is empty.
     */
    private void assertEmptyCommandBox() {
        assertEquals("", commandBoxHandle.getInput());
    }
}
```
###### /java/seedu/address/ui/GraphDisplayTest.java
``` java
public class GraphDisplayTest extends GuiUnitTest {

    private static final SingleGraph NEW_GRAPH_STUB = new SingleGraph("");
    private static final NewGraphDisplayEvent NEW_GRAPH_INITIALISED_STUB =
        new NewGraphDisplayEvent(NEW_GRAPH_STUB, "Stub");

    private GraphDisplayHandle graphDisplayHandle;

    @Before
    public void setUp() {
        GraphDisplay graphDisplay = new GraphDisplay(new LogicStub());
        uiPartRule.setUiPart(graphDisplay);

        graphDisplayHandle = new GraphDisplayHandle(getChildNode(graphDisplay.getRoot(),
            GraphDisplayHandle.DISPLAY_ID));
    }

    @Test
    public void display() {
        // by default, the SwingNode should be be empty
        guiRobot.pauseForHuman();
        assertEquals(null, graphDisplayHandle.getContent());

        // TODO: add tests to check SwingNode content
    }

    /**
     * A default logic stub that have all of the methods failing.
     */
    private class LogicStub implements Logic {
        @Override
        public CommandResult execute(String commandText) {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ListElementPointer getHistorySnapshot() {
            fail("This method should not be called.");
            return null;
        }
    }
}
```
###### /java/systemtests/AutocompletionSystemTest.java
``` java
/**
 * A series of tests to test the auto-completion function.
 *
 * Each test is related to multiple components in Intelli, namely {@code CommandBox} and {@code ResultDisplay}.
 */
public class AutocompletionSystemTest extends AddressBookSystemTest {

    private static final String COMMAND_THAT_FAILS = "@#!";
    private static final String COMMAND_THAT_SUCCEEDS = AddRelationshipCommand.COMMAND_WORD;
    private static final String INCOMPLETE_COMMAND_THAT_SUCCEEDS = AddRelationshipCommand.COMMAND_WORD.substring(0, 4);
    private static final String SHORT_INCOMPLETE_COMMAND_THAT_SUCCEEDS =
            AddRelationshipCommand.COMMAND_WORD.substring(0, 1);

    @Test
    public void checkAutocompletion() {
        CommandBoxHandle commandBoxHandle = getCommandBox();

        //Case: no autocomplete suggestion
        commandBoxHandle.enterInput(COMMAND_THAT_FAILS);
        assertEquals(COMMAND_THAT_FAILS, commandBoxHandle.getInput());

        //Case: single autocomplete suggestion
        commandBoxHandle.enterInput(INCOMPLETE_COMMAND_THAT_SUCCEEDS);
        assertEquals(COMMAND_THAT_SUCCEEDS, commandBoxHandle.getInput());

        //Case: multiple autocomplete suggestions
        commandBoxHandle.enterInput(SHORT_INCOMPLETE_COMMAND_THAT_SUCCEEDS);
        assertEquals("add", commandBoxHandle.getInput());
    }

}
```
###### /java/systemtests/InfoDisplaySystemTest.java
``` java
/**
 * A series of tests to test the information display function.
 *
 * Each test is related to multiple components in Intelli, namely {@code CommandBox} and {@code ResultDisplay}.
 */
public class InfoDisplaySystemTest extends AddressBookSystemTest {

    private static final String COMMAND_THAT_FAILS = "@#!";
    private static final String COMMAND_THAT_SUCCEEDS = AddRelationshipCommand.COMMAND_WORD;
    private static final String COMMAND_THAT_SUCCEEDS_ALIAS = AddRelationshipCommand.COMMAND_ALIAS;
    private static final String SHORT_COMMAND_USAGE = AddRelationshipCommand.SHORT_MESSAGE_USAGE;
    private static final String FORMATTED_SHORT_COMMAND_USAGE = "How to use:\n" + SHORT_COMMAND_USAGE;

    @Test
    public void informationDisplay() {
        CommandBoxHandle commandBoxHandle = getCommandBox();
        ResultDisplayHandle resultDisplayHandle = getInfoDisplay();

        //Case: no command in the command box
        commandBoxHandle.enterInputWithoutAutocompletion("");
        assertEquals("", resultDisplayHandle.getText());

        //Case: no corresponding information displayed
        commandBoxHandle.enterInputWithoutAutocompletion(COMMAND_THAT_FAILS);
        assertEquals("", resultDisplayHandle.getText());

        //Case: corresponding usage information displayed for typed command
        commandBoxHandle.enterInputWithoutAutocompletion(COMMAND_THAT_SUCCEEDS);
        assertEquals(FORMATTED_SHORT_COMMAND_USAGE, resultDisplayHandle.getText());

        //Case: corresponding usage information displayed for typed alias
        commandBoxHandle.enterInputWithoutAutocompletion(COMMAND_THAT_SUCCEEDS_ALIAS);
        assertEquals(FORMATTED_SHORT_COMMAND_USAGE, resultDisplayHandle.getText());
    }
}
```
