package seedu.address.model.graph;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.implementations.SingleNode;

import javafx.collections.ObservableList;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.relationship.RelationshipDirection;


/**
 * This class is a wrapper class of SingleGraph class in GraphStream
 * It is used when creating a new SingleGraph when changes happen in the lastShownList
 */
public class GraphWrapper {

    public static final String MESSAGE_PERSON_DOESNOT_EXIST = "The person does not exist in this address book.";

    private SingleGraph graph;
    private Model model;
    private ObservableList<ReadOnlyPerson> filteredPersons;
    private final String graphId = "ImARandomGraphID";

    private final String nodeAttributePersonName = "PersonName";
    private final String nodeAttributePerson = "Person";

    public GraphWrapper() {
        this.graph = new SingleGraph(graphId);
    }

    public void setData(Model model) {
        this.model = model;
        this.filteredPersons = model.getFilteredPersonList();
    }

    /**
     * Add all the persons in the last displayed list into graph
     * the ID of the node formed for a person is the person's index in the last displayed list
     * @return graph
     */
    private SingleGraph initiateGraphNodes() {
        try {
            for (ReadOnlyPerson person : filteredPersons) {
                SingleNode node = graph.addNode(getNodeIdFromPerson(person));
                node.addAttribute(nodeAttributePersonName, person.getName());
                node.addAttribute(nodeAttributePerson, person);
            }
        } catch (IllegalValueException ive) {
            assert false : "it should not happen.";
        }

        return graph;
    }

    private String getNodeIdFromPerson(ReadOnlyPerson person) throws IllegalValueException {
        requireNonNull(person);
        int indexOfThePerson = filteredPersons.indexOf(person);
        if (indexOfThePerson == -1) {
            throw new IllegalValueException(MESSAGE_PERSON_DOESNOT_EXIST);
        } else {
            return Integer.toString(indexOfThePerson);
        }
    }

    /**
     * fix the format of edge ID
     */
    private String computeEdgeId(ReadOnlyPerson person1, ReadOnlyPerson person2) {
        return  Integer.toString(filteredPersons.indexOf(person1)) + "_"
                + Integer.toString(filteredPersons.indexOf(person2));
    }


    /**
     * Add a directed edge from one person to another
     * remove the previous undirected edge between the two persons (if exists) and add a directed edge
     * @return the directed edge from fromPerson to toPerson
     */
    private Edge addDirectedEdge(ReadOnlyPerson fromPerson, ReadOnlyPerson toPerson) {
        String designatedEdgeId = computeEdgeId(fromPerson, toPerson);
        Edge previousEdge = graph.getEdge(designatedEdgeId);
        if (previousEdge != null && !previousEdge.isDirected()) {
            graph.removeEdge(previousEdge);
        }
        try {
            graph.addEdge(designatedEdgeId, getNodeIdFromPerson(fromPerson),
                    getNodeIdFromPerson(toPerson), true);
        } catch (IllegalValueException ive) {
            assert false : "it should not happen.";
        }

        return graph.getEdge(designatedEdgeId);
    }

    /**
     * Add an undirected edge between two persons
     * remove the previous directed edge between the two persons (if exists) and add an undirected edge
     * @return the undirected edge between firstPerson and secondPerson
     */
    private Edge addUndirectedEdge(ReadOnlyPerson firstPerson, ReadOnlyPerson secondPerson) {
        String designatedEdgeId1 = computeEdgeId(firstPerson, secondPerson);
        Edge previousEdge1 = graph.getEdge(designatedEdgeId1);
        String designatedEdgeId2 = computeEdgeId(secondPerson, firstPerson);
        Edge previousEdge2 = graph.getEdge(designatedEdgeId2);
        if (previousEdge1 != null && previousEdge1.isDirected()) {
            graph.removeEdge(previousEdge1);
        }
        if (previousEdge2 != null && previousEdge2.isDirected()) {
            graph.removeEdge(previousEdge2);
        }
        try {
            graph.addEdge(designatedEdgeId1, getNodeIdFromPerson(firstPerson),
                    getNodeIdFromPerson(secondPerson), false);
        } catch (IllegalValueException ive) {
            assert false : "it should not happen.";
        }

        return graph.getEdge(designatedEdgeId1);
    }

    public Edge addEdge(ReadOnlyPerson firstPerson, ReadOnlyPerson secondPerson, RelationshipDirection direction) {
        requireAllNonNull(firstPerson, secondPerson, direction);
        if (direction.isDirected()) {
            return addDirectedEdge(firstPerson, secondPerson);
        } else {
            return addUndirectedEdge(firstPerson, secondPerson);
        }
    }

    public void clear() {
        this.graph = null;
        this.model = null;
        this.filteredPersons = null;
    }

    private SingleGraph initiateGraphEdges() {
        return graph;
    }
}
