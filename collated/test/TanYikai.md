# TanYikai
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void sortPersons() {
            fail("This method should not be called.");
        }
```
###### \java\seedu\address\logic\commands\RemarkCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addRemark_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withRemark("Some remark").build();
        Person editedPersonTwo = new PersonBuilder(model.getFilteredPersonList().get(INDEX_SECOND_PERSON
                .getZeroBased())).withRemark("Some remark").build();

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON,
                editedPerson.getRemark().value);

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        expectedModel.updatePerson(model.getFilteredPersonList().get(1), editedPersonTwo);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteRemark_success() throws Exception {
        Person editedPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        editedPerson.setRemark(new Remark(""));
        Person editedPerson2 = new Person(model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased()));
        editedPerson.setRemark(new Remark(""));

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON,
                editedPerson.getRemark().toString());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        Model expectedModel2 = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel2.updatePerson(model.getFilteredPersonList().get(1), editedPerson2);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel2);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Index withinBoundIndex = INDEX_FIRST_PERSON;

        //One out of bound index for first index
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, withinBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        //One out of bound index for second index
        RemarkCommand remarkCommand2 = prepareCommand(withinBoundIndex, outOfBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand2, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        //Two out of bound index
        RemarkCommand remarkCommand3 = prepareCommand(outOfBoundIndex, outOfBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand3, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index withinBoundIndex = INDEX_FIRST_PERSON;
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        //one out of bound Index for the first index
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, withinBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        //one out of bound Index for the first index
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemarkCommand remarkCommand2 = prepareCommand(withinBoundIndex, outOfBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand2, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        //two out of bound Index
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemarkCommand remarkCommand3 = prepareCommand(outOfBoundIndex, outOfBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand3, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final RemarkCommand standardCommand = new RemarkCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON,
                new Remark(VALID_REMARK_AMY));

        // same values -> returns true
        RemarkCommand commandWithSameValues = new RemarkCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON,
                new Remark(VALID_REMARK_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON, INDEX_THIRD_PERSON,
                new Remark(VALID_REMARK_AMY))));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON,
                new Remark(VALID_REMARK_BOB))));
    }

    /**
     * Returns an {@code RemarkCommand} with parameters {@code index}, {@code index2} and {@code remark}
     */
    private RemarkCommand prepareCommand(Index index, Index index2, String remark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, index2, new Remark(remark));
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack(), new StorageStub());
        return remarkCommand;
    }
}
```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {

    private Model model;
    private Model expectedModel;
    private Model unsortedModel;
    private SortCommand sortCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        unsortedModel = new ModelManager(getUnsortedAddressBook(), new UserPrefs());

        sortCommand = new SortCommand();
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack(), new StorageStub());
    }

    @Test
    public void execute_sortListIsNotFiltered_showsSameSortList() {
        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_sortListIsFiltered_showsEverything() {
        showFirstPersonOnly(model);
        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        unsortedModel.sortPersons();

        // sort success -> returns true
        assertTrue(expectedModel.equals(unsortedModel));
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_remark() throws Exception {
        final Remark remark = new Remark("Some remark.");
        RemarkCommand command = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_OTHER_INDEX + INDEX_SECOND_PERSON.getOneBased()
                + " " + PREFIX_REMARK + " " + remark.value);
        assertEquals(new RemarkCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, remark), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_sort() throws Exception {
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD) instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " 3") instanceof SortCommand);
    }
```
###### \java\seedu\address\model\person\RemarkTest.java
``` java
public class RemarkTest {

    @Test
    public void equals() {
        Remark remark = new Remark("Hello");

        // same object -> returns true
        assertTrue(remark.equals(remark));

        // same values -> returns true
        Remark remarkCopy = new Remark(remark.value);
        assertTrue(remark.equals(remarkCopy));

        // different types -> returns false
        assertFalse(remark.equals(1));

        // null -> returns false
        assertFalse(remark.equals(null));

        // different person -> returns false
        Remark differentRemark = new Remark("Bye");
        assertFalse(remark.equals(differentRemark));
    }
}
```
###### \java\seedu\address\testutil\TypicalPersons.java
``` java
    public static final Index INDEX_FIRST_PERSON = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_PERSON = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_PERSON = Index.fromOneBased(3);
```
###### \java\seedu\address\testutil\TypicalPersons.java
``` java
    /**
     * Returns an {@code AddressBook} with all the typical persons in unsorted order.
     */
    public static AddressBook getUnsortedAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyPerson person : getUnsortedTypicalPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }
```
###### \java\seedu\address\testutil\TypicalPersons.java
``` java
    public static List<ReadOnlyPerson> getUnsortedTypicalPersons() {
        return new ArrayList<>(Arrays.asList(DANIEL, BENSON, CARL, ALICE, ELLE, FIONA, GEORGE));
    }
}
```
