package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.exceptions.TagNotFoundException;
import seedu.address.model.relationship.Relationship;
import seedu.address.model.relationship.RelationshipDirection;
import seedu.address.model.relationship.exceptions.DuplicateRelationshipException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<ReadOnlyPerson> filteredPersons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = userPrefs;
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    //@@author Xenonym
    public UserPrefs getUserPrefs() {
        return userPrefs;
    }
    //@@author

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    @Override
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    @Override
    public void sortPersons() {
        addressBook.sortData();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    /**
     * Removes a tag with the tagGettingRemoved string
     * @param tagGettingRemoved
     * @throws TagNotFoundException
     * @throws IllegalValueException
     */
    public void removeTag(String tagGettingRemoved) throws TagNotFoundException, IllegalValueException {
        addressBook.removeTag(tagGettingRemoved);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void addRelationship(Index indexFromPerson, Index indexToPerson, RelationshipDirection direction)
            throws IllegalValueException, DuplicateRelationshipException {
        List<ReadOnlyPerson> lastShownList = getFilteredPersonList();

        if (indexFromPerson.getZeroBased() >= lastShownList.size()
                || indexToPerson.getZeroBased() >= lastShownList.size()) {
            throw new IllegalValueException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson fromPerson = lastShownList.get(indexFromPerson.getZeroBased());
        ReadOnlyPerson toPerson = lastShownList.get(indexToPerson.getZeroBased());
        ReadOnlyPerson fromPersonCopy = fromPerson.copy();
        ReadOnlyPerson toPersonCopy = toPerson.copy();
        Relationship relationshipForFromPerson = new Relationship(fromPersonCopy, toPersonCopy, direction);
        Relationship relationshipForToPerson = relationshipForFromPerson;
        if (!direction.isDirected()) {
            relationshipForToPerson = new Relationship(toPersonCopy, fromPersonCopy, direction);
        }


        /*
         Updating the model
         */
        try {
            Person fPerson = (Person) fromPersonCopy;
            Person tPerson = (Person) toPersonCopy;
            fPerson.addRelationship(relationshipForFromPerson);
            tPerson.addRelationship(relationshipForToPerson);
            this.updatePerson(fromPerson, fPerson);
            this.updatePerson(toPerson, tPerson);
        } catch (DuplicateRelationshipException dre) {
            throw new DuplicateRelationshipException();
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("the person's relationship is unmodified. IMPOSSIBLE.");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && filteredPersons.equals(other.filteredPersons);
    }

}
