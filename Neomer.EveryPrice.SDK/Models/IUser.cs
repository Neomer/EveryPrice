﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Models
{
    public interface IUser : IEntity
    {
        String Username { get; set; }

        Guid? Token { get; set; }

        DateTime? TokenExpirationDate { get; set; }

		IUserSecurityProfile SecurityProfile { get; set; }

		IUserProfile Profile { get; set; }
	}
}
