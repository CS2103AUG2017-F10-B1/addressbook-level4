# wenmogu
###### /java/seedu/address/logic/commands/AddCommandTest.java
``` java
        /**
         * This method is called as the construction of a new graph needs the FilteredPersonList.
         * Therefore a dummy list is given.
         */
        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            UniquePersonList dummyList = new UniquePersonList();
            return FXCollections.unmodifiableObservableList(dummyList.asObservableList());
        }

```
###### /java/seedu/address/logic/commands/AddRelationshipCommandTest.java
``` java
public class AddRelationshipCommandTest {
    private static final ConfidenceEstimate DEFAULT_CE = ConfidenceEstimate.UNSPECIFIED;
    private static final Name DEFAULT_NAME = Name.UNSPECIFIED;
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        RelationshipDirection direction = RelationshipDirection.DIRECTED;

        AddRelationshipCommand addRelationshipCommand = prepareCommand(INDEX_FIRST_PERSON,
                INDEX_SECOND_PERSON, direction);

        String expectedMessage = String.format(addRelationshipCommand.MESSAGE_ADD_RELATIONSHIP_SUCCESS,
                INDEX_SECOND_PERSON.toString(), INDEX_FIRST_PERSON.toString(), direction.toString());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addRelationship(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, direction, DEFAULT_NAME, DEFAULT_CE);

        assertCommandSuccess(addRelationshipCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddRelationshipCommand addRelationshipCommand = prepareCommand(outOfBoundIndex,
                INDEX_SECOND_PERSON, RelationshipDirection.UNDIRECTED);

        assertCommandFailure(addRelationshipCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicateRelationship_throwsCommandException() throws Exception {
        RelationshipDirection direction = RelationshipDirection.DIRECTED;

        AddRelationshipCommand addRelationshipCommand = prepareCommand(INDEX_FIRST_PERSON,
                INDEX_SECOND_PERSON, direction);
        addRelationshipCommand.execute();

        assertCommandFailure(addRelationshipCommand, model, AddRelationshipCommand.MESSAGE_DUPLICATED_RELATIONSHIP);
    }

    @Test
    public void execute_addRelationshipOfaDifferentDirection_throwsCommandException() throws Exception {
        AddRelationshipCommand addRelationshipCommand = prepareCommand(INDEX_FIRST_PERSON,
                INDEX_SECOND_PERSON, RelationshipDirection.DIRECTED);
        addRelationshipCommand.execute();

        AddRelationshipCommand addRelationshipCommand1 = prepareCommand(INDEX_SECOND_PERSON,
                INDEX_FIRST_PERSON, RelationshipDirection.DIRECTED);
        AddRelationshipCommand addRelationshipCommand2 = prepareCommand(INDEX_FIRST_PERSON,
                INDEX_SECOND_PERSON, RelationshipDirection.UNDIRECTED);
        AddRelationshipCommand addRelationshipCommand3 = prepareCommand(INDEX_SECOND_PERSON,
                INDEX_FIRST_PERSON, RelationshipDirection.UNDIRECTED);

        assertCommandFailure(addRelationshipCommand1, model, AddRelationshipCommand.MESSAGE_DUPLICATED_RELATIONSHIP);
        assertCommandFailure(addRelationshipCommand2, model, AddRelationshipCommand.MESSAGE_DUPLICATED_RELATIONSHIP);
        assertCommandFailure(addRelationshipCommand3, model, AddRelationshipCommand.MESSAGE_DUPLICATED_RELATIONSHIP);
    }


    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private AddRelationshipCommand prepareCommand(Index fromPersonIndex, Index toPersonIndex,
                                                  RelationshipDirection direction) {
        AddRelationshipCommand command = new AddRelationshipCommand(fromPersonIndex, toPersonIndex, direction,
                DEFAULT_NAME, DEFAULT_CE);
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new StorageStub());
        return command;
    }
}
```
###### /java/seedu/address/logic/commands/DeleteRelationshipCommandTest.java
``` java
public class DeleteRelationshipCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private final AddRelationshipCommand addRelationshipCommand = new AddRelationshipCommand(INDEX_FIRST_PERSON,
            INDEX_SECOND_PERSON, RelationshipDirection.DIRECTED, Name.UNSPECIFIED, ConfidenceEstimate.UNSPECIFIED);
    private final AddRelationshipCommand addRelationshipCommandOnExpectedModel = new AddRelationshipCommand(
            INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, RelationshipDirection.DIRECTED, Name.UNSPECIFIED,
            ConfidenceEstimate.UNSPECIFIED);

    private final Index fromPerson = INDEX_FIRST_PERSON;
    private final Index toPerson = INDEX_SECOND_PERSON;

    @Before
    public void setUp() {
        addRelationshipCommand.setData(model, new CommandHistory(), new UndoRedoStack(), new StorageStub());
        addRelationshipCommandOnExpectedModel.setData(expectedModel, new CommandHistory(), new UndoRedoStack(),
                new StorageStub());
    }

    @Test
    public void execute_validIndexNoRelationshipPreviously_success() throws Exception {
        DeleteRelationshipCommand deleteRelationshipCommand = prepareCommand(fromPerson, toPerson);

        String expectedMessage = String.format(DeleteRelationshipCommand.MESSAGE_DELETE_RELATIONSHIP_SUCCESS,
                fromPerson.toString(), toPerson.toString());

        assertCommandSuccess(deleteRelationshipCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexNoRelationshipPreviously_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteRelationshipCommand deleteRelationshipCommand = prepareCommand(outOfBoundIndex, toPerson);

        assertCommandFailure(deleteRelationshipCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexHaveRelationshipIndexInOrder_success() throws Exception {
        DeleteRelationshipCommand deleteRelationshipCommand = prepareCommand(fromPerson, toPerson);
        String expectedMessage = String.format(DeleteRelationshipCommand.MESSAGE_DELETE_RELATIONSHIP_SUCCESS,
                fromPerson.toString(), toPerson.toString());

        addRelationshipCommand.execute();
        assertCommandSuccess(deleteRelationshipCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexHaveRelationshipInputIndexNotInOrder_success() throws Exception {
        DeleteRelationshipCommand deleteRelationshipCommand = prepareCommand(toPerson, fromPerson);
        String expectedMessage = String.format(DeleteRelationshipCommand.MESSAGE_DELETE_RELATIONSHIP_SUCCESS,
                toPerson.toString(), fromPerson.toString());

        addRelationshipCommand.execute();
        assertCommandSuccess(deleteRelationshipCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexRelationshipToBeDeletedDoesntExist_success() throws Exception {
        Index thirdPerson = Index.fromOneBased(3);

        DeleteRelationshipCommand deleteRelationshipCommand = prepareCommand(thirdPerson, fromPerson);
        String expectedMessage = String.format(DeleteRelationshipCommand.MESSAGE_DELETE_RELATIONSHIP_SUCCESS,
                thirdPerson.toString(), fromPerson.toString());

        addRelationshipCommand.execute();
        addRelationshipCommandOnExpectedModel.execute();
        assertCommandSuccess(deleteRelationshipCommand, model, expectedMessage, expectedModel);
    }

    private DeleteRelationshipCommand prepareCommand(Index firstIndex, Index secondIndex) {
        DeleteRelationshipCommand deleteRelationshipCommand = new DeleteRelationshipCommand(firstIndex, secondIndex);
        deleteRelationshipCommand.setData(model, new CommandHistory(), new UndoRedoStack(), new StorageStub());
        return deleteRelationshipCommand;
    }
}
```
###### /java/seedu/address/logic/commands/RemoveTagCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code RemoveTagCommand}.
 */
public class RemoveTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validTagNameWhichExists_success() throws Exception {
        String tagNameToBeRemoved = "owesMoney";
        RemoveTagCommand removeTagCommand = prepareCommand("owesMoney");

        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_REMOVE_TAG_SUCCESS, tagNameToBeRemoved);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeTag(tagNameToBeRemoved);

        assertCommandSuccess(removeTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validTagNameWhichDoesNotExists_throwsCommandException() throws Exception {
        String tagNameToBeRemoved = VALID_TAG_FRIEND + "ddd";
        RemoveTagCommand removeTagCommand = prepareCommand(tagNameToBeRemoved);

        assertCommandFailure(removeTagCommand, model, RemoveTagCommand.MESSAGE_TAG_NOT_FOUND);
    }


    @Test
    public void equals() {
        RemoveTagCommand removeFirstCommand = new RemoveTagCommand(VALID_TAG_FRIEND);
        RemoveTagCommand removeSecondCommand = new RemoveTagCommand(VALID_TAG_HUSBAND);

        // same object -> returns true
        assertTrue(removeFirstCommand.equals(removeFirstCommand));

        // same values -> returns true
        RemoveTagCommand removeFirstCommandCopy = new RemoveTagCommand(VALID_TAG_FRIEND);
        assertTrue(removeFirstCommand.equals(removeFirstCommandCopy));

        // different types -> returns false
        assertFalse(removeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(removeFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(removeFirstCommand.equals(removeSecondCommand));
    }

    /**
     * Returns a {@code RemoveTagCommand} with the parameter {@code index}.
     */
    private RemoveTagCommand prepareCommand(String tagToBeRemoved) {
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(tagToBeRemoved);
        removeTagCommand.setData(model, new CommandHistory(), new UndoRedoStack(), new StorageStub());
        return removeTagCommand;
    }
}
```
###### /java/seedu/address/logic/parser/AddRelationshipCommandParserTest.java
``` java
public class AddRelationshipCommandParserTest {
    private AddRelationshipCommandParser parser = new AddRelationshipCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        // with long names and confidence estimate - accepted
        assertParseSuccess(parser, "1 2 directed n/friends enemy neighbour ce/12",
                new AddRelationshipCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, RelationshipDirection.DIRECTED,
                        new Name("friends enemy neighbour"), new ConfidenceEstimate(12)));

        // direction ignore case, with long names and confidence estimate - accepted
        assertParseSuccess(parser, "1 2 uNdIREcted n/friends enemy neighbour ce/0.0000000001",
                new AddRelationshipCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, RelationshipDirection.UNDIRECTED,
                        new Name("friends enemy neighbour"), new ConfidenceEstimate(0.0000000001)));

        // direction ignore case, with long names and confidence estimate - accepted
        assertParseSuccess(parser, "1 2 uNdIREcted ce/0.0000000001 n/friends enemy neighbour",
                new AddRelationshipCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, RelationshipDirection.UNDIRECTED,
                        new Name("friends enemy neighbour"), new ConfidenceEstimate(0.0000000001)));
    }

    @Test
    public void parse_optionalFieldsMissing_success() throws Exception {
        // no name and no confidence estimate - accepted
        assertParseSuccess(parser, "1 2 directed",
                new AddRelationshipCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, RelationshipDirection.DIRECTED,
                        Name.UNSPECIFIED, ConfidenceEstimate.UNSPECIFIED));

        // direction ignore cases - accepted
        assertParseSuccess(parser, "1 2 dIREcted", new AddRelationshipCommand(INDEX_FIRST_PERSON,
                INDEX_SECOND_PERSON, RelationshipDirection.DIRECTED, Name.UNSPECIFIED, ConfidenceEstimate.UNSPECIFIED));

        // with long names - accepted
        assertParseSuccess(parser, "1 2 dIREcted n/friends enemy neighbour",
                new AddRelationshipCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, RelationshipDirection.DIRECTED,
                        new Name("friends enemy neighbour"), ConfidenceEstimate.UNSPECIFIED));

        // with confidence estimate - accepted
        assertParseSuccess(parser, "1 2 dIREcted ce/12",
                new AddRelationshipCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, RelationshipDirection.DIRECTED,
                        Name.UNSPECIFIED, new ConfidenceEstimate(12)));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRelationshipCommand.MESSAGE_USAGE);

        // no direction input
        assertParseFailure(parser, "1 2", expectedMessage);

        // not enough person indexes
        assertParseFailure(parser, "1 dIREctedd", expectedMessage);

        //missing all fields
        assertParseFailure(parser, "addre ", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRelationshipCommand.MESSAGE_USAGE);
        String expectedInvalidIndexMsg = String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                AddRelationshipCommand.MESSAGE_USAGE);

        // wrong order of inputs
        assertParseFailure(parser, "1 dIREctedd 2", expectedMessage);

        // redundant spacings
        assertParseFailure(parser, "1 2                dIREctedd", expectedMessage);

        // duplicate index
        assertParseFailure(parser, "1 1 dIREctedd", expectedMessage);

        // invalid index
        assertParseFailure(parser, "0 1 dIREctedd", expectedInvalidIndexMsg);
    }
}
```
###### /java/seedu/address/logic/parser/DeleteRelationshipParserTest.java
``` java
public class DeleteRelationshipParserTest {
    private DeleteRelationshipCommandParser parser = new DeleteRelationshipCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1 2", new DeleteRelationshipCommand(INDEX_FIRST_PERSON,
                INDEX_SECOND_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteRelationshipCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "          ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteRelationshipCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "           1        2", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteRelationshipCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteRelationshipCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "0 2", String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                DeleteRelationshipCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "0 0", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteRelationshipCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/model/UniqueRelationshipListTest.java
``` java
public class UniqueRelationshipListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueRelationshipList uniqueRelationshipList = new UniqueRelationshipList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueRelationshipList.asObservableList().remove(0);
    }
}
```
###### /java/systemtests/DeleteCommandSystemTest.java
``` java
        /* Case: delete a person with relationship -> relationship disappears*/
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        expectedModel = getModel();
        deletedPerson = removePerson(expectedModel, INDEX_FIRST_PERSON);
        expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPerson);
        String addRelationshipCommand = "addre 1 2 directed";
        executeCommand(addRelationshipCommand);
        assertCommandSuccess("delete 1", expectedModel, expectedResultMessage);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */
```
