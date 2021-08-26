import {useState, useEffect, useCallback} from 'react';
import {useDropzone} from 'react-dropzone'
import axios from 'axios';
import './App.css';

const UserProfiles = () => {

  const [userProfiles, setUserProfiles] = useState([]);

  const fetchUserProfiles = () => {
    axios.get(`http://localhost:8080/api/v1/user-profile`)
    .then(res => {
      console.log(res);
      setUserProfiles(res.data);
    })
  }

  useEffect(() => {
    fetchUserProfiles();
  }, []);

  return userProfiles.map((userProfile, index) => {
    return (
      <div key={index}>
        {userProfile.userprofileId ? (
          // eslint-disable-next-line
          <img src={`http://localhost:8080/api/v1/user-profile/${userProfile.userprofileId}/image/download`} /> 
        ) : null        
        }
        <br />
        <br />
        <h1>{userProfile.username}</h1>
        <p>{userProfile.userprofileId}</p>
        <Dropzone {...userProfile} />
        <br />
      </div>
    )
  })
}

const Dropzone = ({userprofileId}) => {
  const onDrop = useCallback(acceptedFiles => {
    const file = acceptedFiles[0];
    console.log(file);

    const formData = new FormData();
    formData.append("file", file);
    axios.post(
      `http://localhost:8080/api/v1/user-profile/${userprofileId}/image/upload`,
      formData,
      {
        headers: {
          "Content-Type" : "multipart/form-data"
        }
      }
    ).then(res => {
      console.log(res.data);
      console.log("upload success!");
    }).catch(err => {
      console.log(err.data);
      console.log("upload failed...")
    });

  }, [userprofileId])
  const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

  return (
    <div {...getRootProps()}>
      <input {...getInputProps()} />
      {
        isDragActive ?
          <p>Drop the files here ...</p> :
          <p>Drag 'n' drop some files here, or click to select files</p>
      }
    </div>
  )
}

function App() {
  return (
    <div className="App">
      <UserProfiles />
    </div>
    
  );
}

export default App;
