import React, {useState} from "react";
import Stack from "@mui/material/Stack";
import Divider from "@mui/material/Divider";
import Autocomplete from "@mui/material/Autocomplete";
import TextField from "@mui/material/TextField";
import Datepicker from "./Datepicker"
const Dropdown = () => {
  const [city, setCity] = useState('');
  const locations = [
    { location: "서울" },
    { location: "광주" },
    { location: "인천" },
  ];

  const schoolSeoul = [
    { school : "건국대학교" },
    { school : "경희대학교" },
    { school : "마포구공유오피스" },
    { school : "서울교육대학교" },
    { school : "서울대학교" },
    { school : "서울시립대학교" },
    { school : "연세대학교" },
    { school : "이화여자대학교" },
    { school : "카이스트경영대학" },
    { school : "한국외국어대학교" },
    { school : "한성대학교" },
  ];
  const schoolSuwon = [
    { school : "성균관대학교(자연과학캠퍼스)" },
  ];
  const schoolIncheon = [
    { school : "송도 글로벌캠퍼스" },
    { school : "연세대학교(송도)" },
  ];

  const schoolGwangju = [
    { school : "광주과학기술원" },
    { school : "전남대학교" },
  ];

  return (
    <Stack
      direction="row"
      spacing={3}
      sx={{ width: 600 }}
      divider={<Divider orientation="vertical" flexItem />}
    >
      <Autocomplete
        id="size-small-standard"
        size="small"
        sx={{ width: 200 }}
        options={locations}
        getOptionLabel={(option) => option.location}
        // defaultValue={locations[0]}
        renderInput={(params) => (
          <TextField
            {...params}
            variant="standard"
            label="location"
            placeholder="지역"
          />
        )}
      />
      <Autocomplete
        id="size-small-standard"
        size="small"
        sx={{ width: 200 }}
        options={locations}
        // if city == "서울" then options = {schoolSeoul}
        // if city == "수원" then options = {schoolSuwon}
        // if city == "광주" then options = {schoolGwnagju}
        // if city == "인천" then options = {schoolIncheon}
        getOptionLabel={(option) => option.school}
        // defaultValue={locations[0]}
        renderInput={(params) => (
          <TextField
            {...params}
            variant="standard"
            label="school"
            placeholder="학교"
          />
        )}
      />
      
      <Datepicker />
    </Stack>
  );
};
export default Dropdown;
