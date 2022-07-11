package gitlet;

/**
 * This will serve as a helper for the git checkout command.
 * The checkout command is like a focal point between the CommitStore and the BlobStore.
 *
 * I've created this so that Commit objects no longer have to concern themselves with
 * having a pointer to the BlobStore. Commits only need to store keys corresponding to blobs
 * on the BlobStore.
 *
 * The git checkout process is:
 *  Given an ID, do a key lookup on the CommitStore to grab the commit of interest
 *  Once you have the commit, use a getter to obtain an ArrayList of keys.
 *  These keys correspond to keys on the BlobStore.
 *
 *  For each key, do a key lookup on blobstore to grab the serialized file.
 *  Perform deserialization
 *  Write that deserialized file to our working directory.
 *  If that file already exists, just overwrite it. Otherwise, create that file.
 */
public class CheckoutHelper {
}
