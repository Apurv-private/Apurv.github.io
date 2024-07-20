#include <iostream>
#include "BST.h" // Ensure this header file includes the necessary class declarations

using namespace std;

int main() {
    try {
        // Create a Binary Search Tree (BST) object
        BST<int> bst;

        // Insert elements into the BST
        cout << "Inserting elements into the BST..." << endl;
        bst.insert(50);
        bst.insert(30);
        bst.insert(70);
        bst.insert(20);
        bst.insert(40);
        bst.insert(60);
        bst.insert(80);

        // Attempt to insert a duplicate element (should throw an exception)
        try {
            bst.insert(30); // Duplicate element
        } catch (const ElementAlreadyExistsInBSTException& e) {
            cout << "Exception caught: " << e.what() << endl;
        }

        // Retrieve elements from the BST
        cout << "\nRetrieving elements from the BST..." << endl;
        try {
            cout << "Element 40 found: " << bst.retrieve(40) << endl;
            cout << "Element 100 found: " << bst.retrieve(100) << endl; // This will throw an exception
        } catch (const ElementDoesNotExistInBSTException& e) {
            cout << "Exception caught: " << e.what() << endl;
        }

        // Traverse the BST in-order
        cout << "\nIn-order traversal of the BST:" << endl;
        bst.traverseInOrder();

    } catch (const exception& e) {
        // Catch any other exceptions
        cout << "An unexpected exception occurred: " << e.what() << endl;
    }

    return 0;
}
